package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GraphQlConfigIntegrationService {
    ResponseEntity<GraphQLApiConfig> getGraphQlApiConfig();

    ResponseEntity<List<CallStatus<GraphQlTab>>> putGraphQlApiConfig(GraphQLApiConfig apiConfig);
}
