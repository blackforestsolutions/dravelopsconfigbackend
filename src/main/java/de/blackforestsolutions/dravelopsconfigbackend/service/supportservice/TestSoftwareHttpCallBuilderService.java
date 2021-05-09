package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.springframework.http.HttpEntity;

import java.util.Map;

public interface TestSoftwareHttpCallBuilderService {
    HttpEntity<Map<GraphQlTab, ApiToken>> buildTestSoftwareHttpEntityWith(GraphQLApiConfig apiConfig);
}
