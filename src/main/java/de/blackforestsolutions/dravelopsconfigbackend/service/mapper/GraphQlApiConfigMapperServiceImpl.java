package de.blackforestsolutions.dravelopsconfigbackend.service.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.blackforestsolutions.dravelopsconfigbackend.model.GraphQLApiConfig;
import org.yaml.snakeyaml.Yaml;

public class GraphQlApiConfigMapperServiceImpl implements GraphQlApiConfigMapperService {

    //TODO Exception Handling machen lol
    @Override
    public GraphQLApiConfig mapYamlToGraphQLApiConfig(String yamlString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(new Yaml().load(yamlString));
            return objectMapper.readValue(jsonString, GraphQLApiConfig.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Exception in YamlToObjects Mapper");
    }

}