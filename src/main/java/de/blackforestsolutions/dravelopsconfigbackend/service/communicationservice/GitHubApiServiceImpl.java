package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice.GitHubMapperService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.GitHubHttpCallBuilderService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

import static de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsHttpCallBuilder.buildUrlWith;

@Service
public class GitHubApiServiceImpl implements GitHubApiService {

    private final GitHubHttpCallBuilderService callBuilderService;
    private final CallService callService;
    private final GitHubMapperService gitHubMapperService;
    private final ApiToken gitHubConfigApiToken;

    @Autowired
    public GitHubApiServiceImpl(GitHubHttpCallBuilderService callBuilderService,
                                CallService callService,
                                GitHubMapperService gitHubMapperService,
                                ApiToken gitHubConfigApiToken) {
        this.callBuilderService = callBuilderService;
        this.callService = callService;
        this.gitHubMapperService = gitHubMapperService;
        this.gitHubConfigApiToken = gitHubConfigApiToken;
    }

    @Override
    public GraphQLApiConfig getGraphQlApiConfig() throws IOException {
        ResponseEntity<String> getResponse = buildAndExecuteCall();
        return gitHubMapperService.extractGraphQlApiConfigFrom(getResponse.getBody());
    }

    @Override
    public void putGraphQlApiConfig(GraphQLApiConfig graphQLApiConfig) throws IOException {
        GitHubFileRequest gitHubFileRequest = gitHubMapperService.extractGitHubFileRequestFrom(graphQLApiConfig);

        ResponseEntity<String> getResponse = buildAndExecuteCall();
        GraphQLApiConfig apiConfig = gitHubMapperService.extractGraphQlApiConfigFrom(getResponse.getBody());
        gitHubFileRequest.setSha(apiConfig.getSha());

        buildAndExecuteCall(gitHubFileRequest);
    }

    private ResponseEntity<String> buildAndExecuteCall() {
        HttpEntity<?> httpEntity = new HttpEntity<>(callBuilderService.buildGitHubHttpHeaderWith(gitHubConfigApiToken));
        return callService.get(getGitHubRequestUrl(), httpEntity);
    }

    private void buildAndExecuteCall(GitHubFileRequest fileRequest) {
        HttpEntity<GitHubFileRequest> httpEntity = new HttpEntity<>(fileRequest, callBuilderService.buildGitHubHttpHeaderWith(gitHubConfigApiToken));
        callService.put(getGitHubRequestUrl(), httpEntity);
    }

    private String getGitHubRequestUrl() {
        ApiToken builder = new ApiToken(gitHubConfigApiToken);
        builder.setPath(callBuilderService.buildGitHubPathWith(gitHubConfigApiToken));
        URL requestUrl = buildUrlWith(builder);
        return requestUrl.toString();
    }
}