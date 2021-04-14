package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsconfigbackend.model.Graphql;
import de.blackforestsolutions.dravelopsconfigbackend.model.Playground;

public class GraphQlObjectMother{

    public static Graphql getGraphQlWithGivenPlayground(Playground playground) {
        Graphql graphql = new Graphql();
        graphql.setPlayground(playground);
        return graphql;
    }
}
