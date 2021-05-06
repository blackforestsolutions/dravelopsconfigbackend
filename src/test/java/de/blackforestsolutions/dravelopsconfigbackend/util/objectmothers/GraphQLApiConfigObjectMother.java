package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GraphQlObjectMother.getGraphQlWithGivenPlayground;
import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.PlaygroundObjectMother.getPlaygroundForSuedbadenApi;

public class GraphQLApiConfigObjectMother{

    public static GraphQLApiConfig getCorrectGraphQLApiConfig() {
        GraphQLApiConfig config = new GraphQLApiConfig();
        config.setGraphql(getGraphQlWithGivenPlayground(getPlaygroundForSuedbadenApi()));
        config.setSha("9252f9ad7f01b5d80e2c346fd9140fdedf47f378");
        return config;
    }
}