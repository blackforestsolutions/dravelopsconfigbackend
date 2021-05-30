package de.blackforestsolutions.dravelopsconfigbackend.controller;

import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.GraphQlConfigIntegrationService;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("configbackend")
public class GraphQlConfigController {

    private final GraphQlConfigIntegrationService configIntegrationService;

    @Autowired
    public GraphQlConfigController(GraphQlConfigIntegrationService configIntegrationService) {
        this.configIntegrationService = configIntegrationService;
    }

    @GetMapping
    public ResponseEntity<GraphQLApiConfig> getGraphQlApiConfig() {
        return configIntegrationService.getGraphQlApiConfig();
    }

    @PutMapping
    public ResponseEntity<List<CallStatus<GraphQlTab>>> putGraphQlApiConfig(GraphQLApiConfig apiConfig) {
        return configIntegrationService.putGraphQlApiConfig(apiConfig);
    }
}