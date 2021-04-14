package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.git;

import de.blackforestsolutions.dravelopsconfigbackend.model.GraphQLApiConfig;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.FileMapperService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.GitCallBuilderService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Service
public class GitServiceImpl implements GitService{

    private static final String TEMPORARY_FOLDER_NAME = "TempConfigBackendFolder";

    private final FileMapperService fileMapperService;
    private final GitCallBuilderService gitCallBuilderService;

    @Autowired
    public GitServiceImpl(FileMapperService fileMapperService, GitCallBuilderService gitCallBuilderService) {
        this.fileMapperService = fileMapperService;
        this.gitCallBuilderService = gitCallBuilderService;
    }

    @Override
    public File pullFileFromGitWith(ApiToken apiToken) throws IOException, GitAPIException {
        File tempDirectory = createTempDirectory();
        System.out.println("TEMP PATH: " + tempDirectory.getPath());
        Git git = cloneRemoteGitRepository(tempDirectory, apiToken);
        String jsonContent = createObjectsFromLatestCommit(git.getRepository(), apiToken);
        git.getRepository().close();
        if(tempDirectory.exists()) {
            deleteAllFilesOfDirectory(tempDirectory);
        }
        return getFileFromByteStream(apiToken, jsonContent.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean pushFileToGitWith(File file, ApiToken apiToken) throws IOException, GitAPIException {
        File tempDirectory = createTempDirectory();
        Git git = cloneRemoteGitRepository(tempDirectory, apiToken);
        boolean wasSuccessful = pushRepositoryToGitHub(git, tempDirectory, file, apiToken);
        git.getRepository().close();
        if(tempDirectory.exists()) {
            deleteAllFilesOfDirectory(tempDirectory);
        }
        return wasSuccessful;
    }


    private String createObjectsFromLatestCommit(Repository repository, ApiToken apiToken) throws IOException, GitAPIException {
        ObjectLoader loader = repository.open(getObjectIdOfYamlFileFromLatestCommit(repository, apiToken));
        return fileMapperService.mapObjectToJson(fileMapperService.mapYamlWith(getFileFromByteStream(apiToken, loader.getBytes()), GraphQLApiConfig.class));
    }


    private ObjectId getObjectIdOfYamlFileFromLatestCommit(Repository repository, ApiToken apiToken) throws IOException, GitAPIException {
        RevCommit latestCommit = new Git(repository).log().setMaxCount(1).call().iterator().next();

        if(latestCommit == null) {
            throw new IllegalStateException("Exception in fetching latest Commit");
        }

        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(latestCommit.getTree());
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create(gitCallBuilderService.buildFileSubPathInGitWith(apiToken)));

        if(! treeWalk.next()) {
            throw new IllegalStateException("Exception in TreeWalk");
        }

        return treeWalk.getObjectId(0);
    }


    private boolean pushRepositoryToGitHub(Git git, File tempDirectory, File jsonFile, ApiToken apiToken) throws IOException, GitAPIException {
        File yamlFile = new File(tempDirectory, apiToken.getPath());
        if(yamlFile.exists() || yamlFile.mkdirs()) {
            PrintWriter writer = new PrintWriter(new File(yamlFile, apiToken.getFilename()));
            writer.write(fileMapperService.mapObjectToYaml(fileMapperService.mapJsonWith(jsonFile, GraphQLApiConfig.class)));
            writer.close();

            git.add().addFilepattern(".").call();
            git.commit().setMessage("Backend Update " + LocalDateTime.now()).call();

            UsernamePasswordCredentialsProvider credentialsProvider =
                    new UsernamePasswordCredentialsProvider(apiToken.getUsername(), apiToken.getPassword());

            return wasUpdateSuccessful(git.push().setCredentialsProvider(credentialsProvider).call());
        }
        return false;
    }

    private boolean wasUpdateSuccessful(Iterable<PushResult> pushResults) {
        Predicate<RemoteRefUpdate> isStatusOK = update -> update.getStatus().equals(RemoteRefUpdate.Status.OK);
        Predicate<PushResult> isPushResultStatusOK = pushResult -> pushResult.getRemoteUpdates().stream().allMatch(isStatusOK);
        return StreamSupport.stream(pushResults.spliterator(), false).allMatch(isPushResultStatusOK);
    }


    private Git cloneRemoteGitRepository(File tempFile, ApiToken apiToken) throws GitAPIException {
        return Git.cloneRepository()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(apiToken.getUsername(), apiToken.getPassword()))
                .setURI(apiToken.getRepository())
                .setDirectory(tempFile)
                .call();
    }


    private File createTempDirectory() throws IOException {
        Path tempPath = Files.createTempDirectory(TEMPORARY_FOLDER_NAME);
        Files.deleteIfExists(tempPath);
        return tempPath.toFile();
    }

    private File getFileFromByteStream(ApiToken apiToken, byte[] byteStream) throws IOException {
        File jsonFile = new File(apiToken.getFilename().substring(0, apiToken.getFilename().length() - 4) + "json");
        FileUtils.writeByteArrayToFile(jsonFile, byteStream);
        return jsonFile;
    }

    private void deleteAllFilesOfDirectory(File file) {
        if(file != null) {
            File[] files = file.listFiles();
            if(files != null) {
                for(File childFile : files) {
                    if(childFile.isDirectory()) deleteAllFilesOfDirectory(childFile);
                    else childFile.deleteOnExit();
                }
            }
            file.deleteOnExit();
        }
    }
}