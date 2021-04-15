package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.git;

import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.RevCommitFetchingException;
import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.TreeWalkException;
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
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class GitServiceImpl implements GitService{

    private static final String TEMPORARY_FOLDER_NAME = "TempConfigBackendFolder";
    private static final String BACKEND_UPDATE = "Backend Update ";
    private static final boolean IS_RECURSIVE_GIT_DIRECTORY_SEARCH = true;
    private static final int POSITION_OF_FIRST_OBJECT_ID = 0;
    private static final int MAX_AMOUNT_OF_FETCHED_REV_COMMITS = 1;
    private static final String CONSIDER_ALL_FILES_FILEPATTERN = ".";
    private static final boolean IS_SEQUENTIAL_STREAM = true;

    @Override
    public File pullFileFromGitWith(ApiToken apiToken) throws IOException, GitAPIException {
        File tempDirectory = createTempDirectory(apiToken);
        Git git = cloneRemoteGitRepository(tempDirectory, apiToken);

        ObjectLoader objectLoader = createObjectLoaderOfLatestCommit(git.getRepository(), apiToken);
        File returnFile = createFileFromByteStream(tempDirectory, apiToken, objectLoader.getBytes());

        git.getRepository().close();
        if(tempDirectory.exists()) {
            deleteAllFilesOfDirectory(tempDirectory);
        }
        return returnFile;
    }

    @Override
    public List<RemoteRefUpdate.Status> pushFileToGitWith(File file, ApiToken apiToken) throws IOException, GitAPIException {
        File tempDirectory = createTempDirectory(apiToken);
        Git git = cloneRemoteGitRepository(tempDirectory, apiToken);

        FileUtils.copyFile(file, tempDirectory);

        List<RemoteRefUpdate.Status> updateStatusList = pushRepositoryToGitHub(git, apiToken);

        git.getRepository().close();

        if(tempDirectory.exists()) {
            deleteAllFilesOfDirectory(tempDirectory);
        }
        return updateStatusList;
    }


    private ObjectLoader createObjectLoaderOfLatestCommit(Repository repository, ApiToken apiToken) throws IOException, GitAPIException {
        return repository.open(getObjectIdOfFileFromLatestCommit(repository, apiToken));
    }

    private ObjectId getObjectIdOfFileFromLatestCommit(Repository repository, ApiToken apiToken) throws IOException, GitAPIException {
        Optional<RevCommit> latestCommit = Optional.ofNullable(new Git(repository)
                .log()
                .setMaxCount(MAX_AMOUNT_OF_FETCHED_REV_COMMITS)
                .call()
                .iterator()
                .next());

        return walkThroughLatestCommit(repository, apiToken, latestCommit).getObjectId(POSITION_OF_FIRST_OBJECT_ID);
    }

    private TreeWalk walkThroughLatestCommit(Repository repository, ApiToken apiToken, Optional<RevCommit> latestCommit) throws IOException {
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(latestCommit.orElseThrow(RevCommitFetchingException::new).getTree());
        treeWalk.setRecursive(IS_RECURSIVE_GIT_DIRECTORY_SEARCH);
        treeWalk.setFilter(PathFilter.create(apiToken.getPath()));

        if(! treeWalk.next()) {
            throw new TreeWalkException();
        }

        return treeWalk;
    }

    private List<RemoteRefUpdate.Status> pushRepositoryToGitHub(Git git, ApiToken apiToken) throws GitAPIException {
        git.add().addFilepattern(CONSIDER_ALL_FILES_FILEPATTERN).call();
        git.commit().setMessage(BACKEND_UPDATE.concat(": ").concat(LocalDateTime.now().toString())).call();
        return getPushUpdateStatus(git.push()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(apiToken.getUsername(), apiToken.getPassword()))
                .call());
    }

    private List<RemoteRefUpdate.Status> getPushUpdateStatus(Iterable<PushResult> pushResults) {
        Function<PushResult, Stream<RemoteRefUpdate.Status>> mapPushResultToRefUpdates = pushResult ->
                pushResult.getRemoteUpdates()
                        .stream()
                        .map(RemoteRefUpdate::getStatus);

        return StreamSupport.stream(pushResults.spliterator(), IS_SEQUENTIAL_STREAM)
                .flatMap(mapPushResultToRefUpdates)
                .collect(Collectors.toList());
    }

    private Git cloneRemoteGitRepository(File tempFile, ApiToken apiToken) throws GitAPIException {
        return Git.cloneRepository()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(apiToken.getUsername(), apiToken.getPassword()))
                .setURI(apiToken.getRepository())
                .setDirectory(tempFile)
                .call();
    }

    private File createTempDirectory(ApiToken apiToken) throws IOException {
        Path tempPath = Files.createTempDirectory(TEMPORARY_FOLDER_NAME.concat(apiToken.getPath()));
        Files.deleteIfExists(tempPath);
        return tempPath.toFile();
    }

    private File createFileFromByteStream(File tempDirectory, ApiToken apiToken, byte[] byteStream) throws IOException {
        File file = new File(tempDirectory.getPath(), apiToken.getFilename());
        FileUtils.writeByteArrayToFile(file, byteStream);
        return file;
    }

    private void deleteAllFilesOfDirectory(File file) {
        if(Optional.ofNullable(file).isPresent()) {
            File[] files = file.listFiles();
            if(Optional.ofNullable(files).isPresent()) {
                for(File childFile : files) {
                    if(childFile.isDirectory()) {
                        deleteAllFilesOfDirectory(childFile);
                    } else {
                        childFile.deleteOnExit();
                    }
                }
            }
            file.deleteOnExit();
        }
    }
}