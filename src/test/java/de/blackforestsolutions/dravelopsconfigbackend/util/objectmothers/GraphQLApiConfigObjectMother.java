package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsconfigbackend.model.GraphQLApiConfig;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GraphQlObjectMother.getGraphQlWithGivenPlayground;
import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.PlaygroundObjectMother.getPlaygroundForSuedbadenApi;

public class GraphQLApiConfigObjectMother{


    public static GraphQLApiConfig getCorrectGraphQLApiConfig() {
        GraphQLApiConfig config = new GraphQLApiConfig();
        config.setGraphql(getGraphQlWithGivenPlayground(getPlaygroundForSuedbadenApi()));
        return config;
    }
}