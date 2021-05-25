package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;

public interface GitHubApiService{
    CallStatus<GraphQLApiConfig> getGraphQlApiConfig();

    CallStatus<String> putGraphQlApiConfig(GraphQLApiConfig graphQLApiConfig) throws JsonProcessingException;
}
