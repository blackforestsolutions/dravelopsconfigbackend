package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;

import java.util.List;

public interface TestSoftwareService {
    List<CallStatus<GraphQlTab>> getTestSoftwareResultWith(GraphQLApiConfig apiConfig);
}
