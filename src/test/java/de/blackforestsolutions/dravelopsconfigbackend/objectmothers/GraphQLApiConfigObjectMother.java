package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Tab;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQlObjectMother.getGraphQlWithNoEmptyFields;
import static de.blackforestsolutions.dravelopsdatamodel.GraphQlTab.JOURNEY_SUBSCRIPTION;

public class GraphQLApiConfigObjectMother {

    private static final String SHA = "466c1149863ba873988b38a0aed477bfce968e7c";

    public static GraphQLApiConfig getGraphQLApiConfigWithNoEmptyFields() {
        GraphQLApiConfig config = new GraphQLApiConfig();

        config.setGraphql(getGraphQlWithNoEmptyFields());
        config.setSha(SHA);

        return config;
    }

    public static GraphQLApiConfig getGraphQLApiConfigWithMissingJourneySubscriptionTabExceptName() {
        GraphQLApiConfig config = new GraphQLApiConfig();

        config.setGraphql(getGraphQlWithNoEmptyFields());
        Tab tab = new Tab();
        tab.setName(config.getGraphql().getPlayground().getTabs().get(JOURNEY_SUBSCRIPTION.toString()).getName());
        config.getGraphql().getPlayground().getTabs().put(JOURNEY_SUBSCRIPTION.toString(), tab);
        config.setSha(SHA);

        return config;
    }
}