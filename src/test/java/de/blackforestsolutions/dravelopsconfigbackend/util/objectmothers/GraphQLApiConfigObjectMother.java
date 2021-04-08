package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsconfigbackend.model.*;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GraphQlObjectMother.getGraphQlWithGivenPlayground;
import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.PlaygroundObjectMother.getPlaygroundForSuedbadenApi;

public class GraphQLApiConfigObjectMother {


    public static GraphQLApiConfig getCorrectGraphQLApiConfig(){
        return GraphQLApiConfig.builder()
                .graphql(getGraphQlWithGivenPlayground(getPlaygroundForSuedbadenApi()))
                .build();
    }
}