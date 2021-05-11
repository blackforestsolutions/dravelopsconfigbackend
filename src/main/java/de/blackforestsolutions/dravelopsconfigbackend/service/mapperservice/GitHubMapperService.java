package de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;

public interface GitHubMapperService{
    GraphQLApiConfig extractGraphQlApiConfigFrom(String jsonBody) throws JsonProcessingException;

    GitHubFileRequest extractGitHubFileRequestFrom(GraphQLApiConfig graphQLApiConfig) throws JsonProcessingException;
}
