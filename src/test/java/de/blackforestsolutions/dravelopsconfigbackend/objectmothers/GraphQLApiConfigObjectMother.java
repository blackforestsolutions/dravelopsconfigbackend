package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQlObjectMother.getGraphQlWithMissingJourneySubscription;
import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQlObjectMother.getGraphQlWithNoEmptyFields;

public class GraphQLApiConfigObjectMother {

    private static final String SHA = "466c1149863ba873988b38a0aed477bfce968e7c";

    public static GraphQLApiConfig getGraphQLApiConfigWithNoEmptyFields() {
        GraphQLApiConfig config = new GraphQLApiConfig();

        config.setGraphql(getGraphQlWithNoEmptyFields());
        config.setSha(SHA);

        return config;
    }

    public static GraphQLApiConfig getGraphQLApiConfigWithEmptyJourneySubscriptionTab() {
        GraphQLApiConfig config = new GraphQLApiConfig();

        config.setGraphql(getGraphQlWithMissingJourneySubscription());
        config.setSha(SHA);

        return config;
    }
}