package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQlObjectMother.getGraphQlWithNoEmptyFields;

public class GraphQLApiConfigObjectMother {

    public static GraphQLApiConfig getGraphQLApiConfigWithNoEmptyFields() {
        GraphQLApiConfig config = new GraphQLApiConfig();

        config.setGraphql(getGraphQlWithNoEmptyFields());
        config.setSha("9252f9ad7f01b5d80e2c346fd9140fdedf47f378");

        return config;
    }
}