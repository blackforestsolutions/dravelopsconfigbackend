package de.blackforestsolutions.dravelopsconfigbackend.service.mapper;

import de.blackforestsolutions.dravelopsconfigbackend.model.GraphQLApiConfig;

public interface GraphQlApiConfigMapperService{

    GraphQLApiConfig mapYamlToGraphQLApiConfig(String yamlString);

}