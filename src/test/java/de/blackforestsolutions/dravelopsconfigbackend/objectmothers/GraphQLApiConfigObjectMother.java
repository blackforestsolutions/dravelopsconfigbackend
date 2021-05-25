package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQlObjectMother.getGraphQlWithNoEmptyFields;

public class GraphQLApiConfigObjectMother {

    public static GraphQLApiConfig getGraphQLApiConfigWithNoEmptyFields() {
        GraphQLApiConfig config = new GraphQLApiConfig();

        config.setGraphql(getGraphQlWithNoEmptyFields());
        config.setSha("466c1149863ba873988b38a0aed477bfce968e7c");

        return config;
    }
}