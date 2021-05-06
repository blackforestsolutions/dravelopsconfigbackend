package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Graphql;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Playground;

public class GraphQlObjectMother{

    public static Graphql getGraphQlWithGivenPlayground(Playground playground) {
        Graphql graphql = new Graphql();
        graphql.setPlayground(playground);
        return graphql;
    }
}
