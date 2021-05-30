package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;

import java.io.IOException;

public interface GitHubApiService {
    GraphQLApiConfig getGraphQlApiConfig() throws IOException;

    void putGraphQlApiConfig(GraphQLApiConfig graphQLApiConfig) throws IOException;
}
