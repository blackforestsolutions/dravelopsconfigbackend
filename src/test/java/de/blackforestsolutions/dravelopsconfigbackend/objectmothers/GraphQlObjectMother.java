package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Graphql;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.PlaygroundObjectMother.getPlayGroundWithEmptyJourneySubscriptionTab;
import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.PlaygroundObjectMother.getPlaygroundWithNoEmptyFields;

public class GraphQlObjectMother {

    public static Graphql getGraphQlWithNoEmptyFields() {
        Graphql graphql = new Graphql();

        graphql.setPlayground(getPlaygroundWithNoEmptyFields());

        return graphql;
    }

    public static Graphql getGraphQlWithMissingJourneySubscription() {
        Graphql graphql = new Graphql();

        graphql.setPlayground(getPlayGroundWithEmptyJourneySubscriptionTab());

        return graphql;
    }
}
