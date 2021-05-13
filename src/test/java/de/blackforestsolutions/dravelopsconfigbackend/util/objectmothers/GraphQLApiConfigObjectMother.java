package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GraphQlObjectMother.getGraphQlWithGivenPlayground;
import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.PlaygroundObjectMother.getPlaygroundForSuedbadenApi;

public class GraphQLApiConfigObjectMother{

    public static GraphQLApiConfig getCorrectGraphQLApiConfig() {
        GraphQLApiConfig config = new GraphQLApiConfig();
        config.setGraphql(getGraphQlWithGivenPlayground(getPlaygroundForSuedbadenApi()));
        config.setSha("466c1149863ba873988b38a0aed477bfce968e7c");
        return config;
    }
}