package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice.GitHubMapperService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.GitHubHttpCallBuilderService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.Status;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;

import static de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsHttpCallBuilder.buildUrlWith;

@Service
public class GitHubApiServiceImpl implements GitHubApiService{

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
        this.gitHubConfigApiToken = gitHubConfigApiToken;
        this.gitHubMapperService = gitHubMapperService;
    }

    @Override
    public CallStatus<GraphQLApiConfig> getGraphQlApiConfig() {
        try {
            ResponseEntity<String> result = buildAndExecuteCall();
            GraphQLApiConfig apiConfig = gitHubMapperService.extractGraphQlApiConfigFrom(result.getBody());
            return new CallStatus<>(apiConfig, Status.SUCCESS, null);
        } catch(Exception e) {
            return new CallStatus<>(null, Status.FAILED, e);
        }
    }

    @Override
    public CallStatus<String> pushGraphQlApiConfig(GraphQLApiConfig graphQLApiConfig) {
        try {
            GitHubFileRequest gitHubFileRequest = gitHubMapperService.extractGitHubFileRequestFrom(graphQLApiConfig);
            ResponseEntity<String> result = buildAndExecuteCall(gitHubFileRequest);
            return new CallStatus<>(result.getBody(), Status.SUCCESS, null);
        } catch(Exception e) {
            return new CallStatus<>(null, Status.FAILED, e);
        }
    }

    private ResponseEntity<String> buildAndExecuteCall() {
        HttpEntity<?> httpEntity = new HttpEntity<>(callBuilderService.buildGitHubHttpHeader(gitHubConfigApiToken));
        return callService.get(getGitHubRequestString(), httpEntity);
    }

    private ResponseEntity<String> buildAndExecuteCall(GitHubFileRequest fileRequest) {
        HttpEntity<GitHubFileRequest> httpEntity = new HttpEntity<>(fileRequest, callBuilderService.buildGitHubHttpHeader(gitHubConfigApiToken));
        return callService.post(getGitHubRequestString(), httpEntity);
    }

    private String getGitHubRequestString() {
        ApiToken builder = new ApiToken(gitHubConfigApiToken);
        builder.setPath(callBuilderService.buildGitHubPathWith(gitHubConfigApiToken));
        URL requestUrl = buildUrlWith(builder);
        return requestUrl.toString();
    }
}