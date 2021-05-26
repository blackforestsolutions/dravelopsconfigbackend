package de.blackforestsolutions.dravelopsconfigbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.GitHubApiService;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("configbackend")
public class GraphQlConfigController {

    private final GitHubApiService apiService;

    @Autowired
    public GraphQlConfigController(GitHubApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping
    public CallStatus<GraphQLApiConfig> get() {
        return apiService.getGraphQlApiConfig();
    }

    @PutMapping
    public CallStatus<String> put(GraphQLApiConfig apiConfig) throws JsonProcessingException {
        return apiService.putGraphQlApiConfig(apiConfig);
    }
}