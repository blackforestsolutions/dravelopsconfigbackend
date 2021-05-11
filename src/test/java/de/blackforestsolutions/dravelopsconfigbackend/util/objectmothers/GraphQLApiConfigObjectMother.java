package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GraphQlObjectMother.getGraphQlWithGivenPlayground;
import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.PlaygroundObjectMother.getPlaygroundForSuedbadenApi;

public class GraphQLApiConfigObjectMother{

    public static GraphQLApiConfig getCorrectGraphQLApiConfig() {
        GraphQLApiConfig config = new GraphQLApiConfig();
        config.setGraphql(getGraphQlWithGivenPlayground(getPlaygroundForSuedbadenApi()));
        config.setSha("06aae133c79ab9b04e2aac8cc178a7d36d33f4b9");
        return config;
    }
}