package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.git;

import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.RevCommitFetchingException;
import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.TreeWalkException;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class GitServiceImpl implements GitService{

    private static final String TEMPORARY_PULL_FOLDER = "TempConfigBackendPullFolder";
    private static final String TEMPORARY_PUSH_FOLDER = "TempConfigBackendPushFolder";
    private static final String BACKEND_UPDATE = "Backend Update ";
    private static final boolean IS_RECURSIVE_GIT_DIRECTORY_SEARCH = true;
    private static final int POSITION_OF_FIRST_OBJECT_ID = 0;
    private static final int MAX_AMOUNT_OF_FETCHED_REV_COMMITS = 1;
    private static final String CONSIDER_ALL_FILES_FILEPATTERN = ".";
    private static final boolean IS_SEQUENTIAL_STREAM = true;
    private static final String FILE_PATH_SEPARATOR = "/";

    @Override
    public File pullFileFromGitWith(ApiToken apiToken) throws IOException, GitAPIException {
        File tempDirectory = createTempPullDirectory(apiToken);
        Git git = cloneRemoteGitRepository(tempDirectory, apiToken);
        try {
            ObjectLoader objectLoader = createObjectLoaderOfLatestCommit(git, apiToken);
            //ObjectLoader objectLoader = loadRemote(apiToken, "master");;
            String test = new String(objectLoader.getBytes(), StandardCharsets.UTF_8);

            File returnFile = createFileFromByteStream(tempDirectory, apiToken, objectLoader.getBytes());

            /*
            log.error("TESTTT: " + returnFile.getPath());
            Files.lines(returnFile.toPath(), StandardCharsets.US_ASCII).collect(Collectors.toList()).forEach(charset ->{
                log.error("Charset: " + charset);
            });

             */

            git.getRepository().close();

            git.close();


            //FileUtils.deleteDirectory(tempDirectory);

            //FileUtils.deleteDirectory(tempDirectory);
            //deleteAllFilesOfDirectory(tempDirectory);

            return returnFile;
        } catch(Exception e) {
            log.error("Pull Error: ", e);
            return pullFileFromGitWith(new ApiToken(apiToken));
        }
    }

    @Override
    public List<RemoteRefUpdate.Status> pushFileToGitWith(File file, ApiToken apiToken) throws IOException, GitAPIException {
        File tempDirectory = createTempMainDirectory(TEMPORARY_PUSH_FOLDER);

        Git git = cloneRemoteGitRepository(tempDirectory, apiToken);

        createPushFolderHierarchy(tempDirectory, file, apiToken);

        List<RemoteRefUpdate.Status> updateStatusList = pushRepositoryToGitHub(git, apiToken);

        git.getRepository().close();

        git.close();

        return updateStatusList;
    }

    private ObjectLoader createObjectLoaderOfLatestCommit(Git git, ApiToken apiToken) throws IOException, GitAPIException {
        return git.getRepository().open(getObjectIdOfFileFromLatestCommit(git, apiToken));
    }

    private ObjectId getObjectIdOfFileFromLatestCommit(Git git, ApiToken apiToken) throws IOException, GitAPIException {
        return walkThroughLatestCommit(git, apiToken).getObjectId(POSITION_OF_FIRST_OBJECT_ID);
    }

    private TreeWalk walkThroughLatestCommit(Git git, ApiToken apiToken) throws IOException, GitAPIException {
        Optional<RevCommit> latestCommit = getLatestCommit(git);

        TreeWalk treeWalk = new TreeWalk(git.getRepository());
        treeWalk.addTree(latestCommit.orElseThrow(RevCommitFetchingException::new).getTree());
        treeWalk.setRecursive(IS_RECURSIVE_GIT_DIRECTORY_SEARCH);

        String directoryPath =
                apiToken.getPath()
                        .concat(FILE_PATH_SEPARATOR)
                        .concat(apiToken.getFilename());

        treeWalk.setFilter(PathFilter.create(directoryPath));


        if(! treeWalk.next()) {
            throw new TreeWalkException();
        }

        return treeWalk;
    }


    private Optional<RevCommit> getLatestCommit(Repository repository) throws GitAPIException {
        return Optional.ofNullable(new Git(repository)
                .log()
                .setMaxCount(MAX_AMOUNT_OF_FETCHED_REV_COMMITS)
                .call()
                .iterator()
                .next());
    }

    private Optional<RevCommit> getLatestCommit(Git git) throws IOException, GitAPIException {
        RevCommit youngestCommit = null;
        RevWalk walk = new RevWalk(git.getRepository());

        List<Ref> branchList = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
        for(Ref branch : branchList) {
            RevCommit commit = walk.parseCommit(branch.getObjectId());
            if(youngestCommit == null || commit
                    .getAuthorIdent()
                    .getWhen()
                    .compareTo(youngestCommit.getAuthorIdent().getWhen()) > 0) {
                youngestCommit = commit;
            }
        }
        walk.close();
        return Optional.ofNullable(youngestCommit);
    }


    private List<RemoteRefUpdate.Status> pushRepositoryToGitHub(Git git, ApiToken apiToken) throws GitAPIException {
        git.add().addFilepattern(CONSIDER_ALL_FILES_FILEPATTERN).setUpdate(true).call();
        git.commit().setMessage(BACKEND_UPDATE.concat(": ").concat(LocalDateTime.now().toString())).call();

        Iterable<PushResult> pushResults = git
                .push()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(apiToken.getUsername(), apiToken.getPassword()))
                .call();

        return getPushUpdateStatus(pushResults);
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

    private File createTempPullDirectory(ApiToken apiToken) throws IOException {
        File subFileDirectories = new File(createTempMainDirectory(TEMPORARY_PULL_FOLDER), apiToken.getPath());
        subFileDirectories.mkdirs();
        return subFileDirectories;
    }

    private void createPushFolderHierarchy(File tempDirectory, File file, ApiToken apiToken) throws IOException {
        File subDirectory = new File(tempDirectory, apiToken.getPath());
        subDirectory.mkdirs();
        Files.move(file.toPath(), new File(subDirectory, apiToken.getFilename()).toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private File createTempMainDirectory(String directoryName) throws IOException {
        Path tempPath = Files.createTempDirectory(directoryName);
        File tempDirectory = tempPath.toFile();

        if(tempDirectory.exists()) {
            //deleteAllFilesOfDirectory(tempDirectory);
            FileUtils.deleteDirectory(tempDirectory);
        }

        Files.deleteIfExists(tempPath);

        return tempDirectory;
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
                        childFile.delete();
                    }
                }
            }
            file.delete();
        }
    }


    private ObjectLoader loadRemote(ApiToken apiToken, String branch) throws Exception {
        InMemoryRepository repo = new InMemoryRepository(new DfsRepositoryDescription());
        Git git = new Git(repo);
        git.fetch()
                .setRemote(apiToken.getRepository())
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(apiToken.getUsername(), apiToken.getPassword()))
                .setRefSpecs(new RefSpec("+refs/heads/*:refs/heads/*"))
                .call();
        repo.getObjectDatabase();

        //ObjectId lastCommitId = repo.resolve("refs/heads/"+ branch);
        Optional<RevCommit> latestCommit = getLatestCommit(repo);

        RevWalk revWalk = new RevWalk(repo);
        RevCommit commit = revWalk.parseCommit(latestCommit.orElseThrow(RevCommitFetchingException::new));
        RevTree tree = commit.getTree();


        TreeWalk treeWalk = new TreeWalk(repo);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(IS_RECURSIVE_GIT_DIRECTORY_SEARCH);

        String directoryPath =
                apiToken.getPath()
                        .concat(FILE_PATH_SEPARATOR)
                        .concat(apiToken.getFilename());

        treeWalk.setFilter(PathFilter.create(directoryPath));

        if(! treeWalk.next()) {
            return null;
        }

        ObjectId objectId = treeWalk.getObjectId(0);
        return repo.open(objectId);
    }
}