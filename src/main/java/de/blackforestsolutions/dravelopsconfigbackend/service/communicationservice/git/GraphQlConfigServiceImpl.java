package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.git;

import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.ExceptionHandlerService;
import de.blackforestsolutions.dravelopsconfigbackend.model.GraphQLApiConfig;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.FileMapperService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.GitCallBuilderService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class GraphQlConfigServiceImpl{

    private final ExceptionHandlerService exceptionHandlerService;
    private final GitService gitService;
    private final FileMapperService fileMapperService;
    private final GitCallBuilderService gitCallBuilderService;

    private static final String TEMPORARY_FOLDER_NAME = "TempConfigBackendFolder";

    @Autowired
    public GraphQlConfigServiceImpl(ExceptionHandlerService exceptionHandlerService, GitService gitService, FileMapperService fileMapperService, GitCallBuilderService gitCallBuilderService) {
        this.exceptionHandlerService = exceptionHandlerService;
        this.gitService = gitService;
        this.fileMapperService = fileMapperService;
        this.gitCallBuilderService = gitCallBuilderService;
    }

    public GraphQLApiConfig getGraphQlConfigsFromGit(ApiToken apiToken) throws GitAPIException, IOException {
        try {

        } catch(Exception ignore) {
            // todo
        }

        ApiToken gitToken = buildGitServiceApiTokenWith(apiToken);
        File yaml = gitService.pullFileFromGitWith(gitToken);
        return fileMapperService.mapYamlWith(yaml, GraphQLApiConfig.class);
    }

    public List<RemoteRefUpdate.Status> pushFileToGitHub(File file, ApiToken apiToken) throws IOException, GitAPIException {
        return gitService.pushFileToGitWith(file, apiToken);
    }

    /*
    PrintWriter writer = new PrintWriter(new File(directory, apiToken.getFilename()));
        writer.write(fileMapperService.mapObjectToYaml(fileMapperService.mapJsonWith(file, GraphQLApiConfig.class)));
        writer.close();
     */

    private ApiToken buildGitServiceApiTokenWith(ApiToken apiToken) {
        ApiToken gitServiceApiToken = new ApiToken(apiToken);
        gitServiceApiToken.setFilename(gitCallBuilderService.buildFileName(apiToken));
        gitServiceApiToken.setPath(gitCallBuilderService.buildFileSubPathInGitWith(apiToken));
        return gitServiceApiToken;
    }
}
