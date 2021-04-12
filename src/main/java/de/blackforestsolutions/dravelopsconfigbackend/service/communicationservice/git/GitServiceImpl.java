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
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;

@Service
public class GitServiceImpl implements GitService {

    private final String TEMPORARY_FOLDER_NAME = "TempConfigBackendFolder";
    private final String TEMP_FILE_NAME = "tempFileName";

    private final FileMapperService fileMapperService;
    private final GitCallBuilderService gitCallBuilderService;

    @Autowired
    public GitServiceImpl(FileMapperService fileMapperService, GitCallBuilderService gitCallBuilderService){
        this.fileMapperService = fileMapperService;
        this.gitCallBuilderService = gitCallBuilderService;
    }

    private Git cloneRemoteGitRepository(File tempFile, ApiToken apiToken) throws GitAPIException {
        return Git.cloneRepository()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(apiToken.getUsername(), apiToken.getPassword()))
                .setURI(apiToken.getRepository())
                .setDirectory(tempFile)
                .call();
    }

    private File createTempFile() throws IOException {
        File tempFile = File.createTempFile(TEMPORARY_FOLDER_NAME, "");
        Files.deleteIfExists(tempFile.toPath());
        return tempFile;
    }



    @Override
    public File pullFileFromGitWith(ApiToken apiToken) throws IOException, GitAPIException {
        File tempRepositoryDirectory = createTempFile();
        Git git = cloneRemoteGitRepository(tempRepositoryDirectory, apiToken);
        String jsonContent = createObjectsFromLatestCommit(git.getRepository(), apiToken);
        git.getRepository().close();

        File jsonFile = new File(apiToken.getFilename());
        FileUtils.writeByteArrayToFile(jsonFile, jsonContent.getBytes(StandardCharsets.UTF_8));
        return jsonFile;
    }

    private String createObjectsFromLatestCommit(Repository repository, ApiToken apiToken) throws IOException, GitAPIException {
        ObjectLoader loader = repository.open(getObjectIdOfYamlFileFromLatestCommit(repository, apiToken));
        File yamlFile = new File(apiToken.getFilename());
        FileUtils.writeByteArrayToFile(yamlFile, loader.getBytes());
        return fileMapperService.mapObjectToJson(fileMapperService.mapYamlWith(yamlFile, GraphQLApiConfig.class));
    }

    private ObjectId getObjectIdOfYamlFileFromLatestCommit(Repository repository, ApiToken apiToken) throws IOException, GitAPIException {
        RevCommit latestCommit = new Git(repository).log().setMaxCount(1).call().iterator().next();

        if(latestCommit == null) throw new IllegalStateException("Exception in fetching latest Commit");

        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(latestCommit.getTree());
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create(gitCallBuilderService.buildFileSubPathInGitWith(apiToken)));

        if(!treeWalk.next()) throw new IllegalStateException("Exception in TreeWalk");

        return treeWalk.getObjectId(0);
    }



    @Override
    public void pushFileToGitWith(File file, ApiToken apiToken) throws IOException, GitAPIException {
        File tempDirectory = createTempFile();
        Git git = cloneRemoteGitRepository(tempDirectory, apiToken);
        pushRepositoryToGitHub(git, tempDirectory, file, apiToken);
        git.getRepository().close();
    }

    private void pushRepositoryToGitHub(Git git, File tempDirectory, File jsonFile, ApiToken apiToken) throws IOException, GitAPIException {
        File yamlFile = new File(tempDirectory, apiToken.getPath());
        if(yamlFile.exists() || yamlFile.mkdirs()) {
            PrintWriter writer = new PrintWriter(new File(yamlFile, apiToken.getFilename()));
            writer.write(fileMapperService.mapObjectToYaml(fileMapperService.mapJsonWith(jsonFile, GraphQLApiConfig.class)));
            writer.close();

            git.add().addFilepattern(".").call();
            git.commit().setMessage("Backend Update " + LocalDateTime.now()).call();
            git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(apiToken.getUsername(), apiToken.getPassword())).call();
        }
    }
}