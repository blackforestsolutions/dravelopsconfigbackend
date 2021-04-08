package de.blackforestsolutions.dravelopsconfigbackend.service.mapper;

import de.blackforestsolutions.dravelopsconfigbackend.model.GraphQLApiConfig;
import de.blackforestsolutions.dravelopsdatamodel.testutil.TestUtils;
import org.junit.jupiter.api.Test;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GraphQLApiConfigObjectMother.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GraphQlApiConfigMapperServiceTest{

    private final GraphQlApiConfigMapperService classUnderTest = new GraphQlApiConfigMapperServiceImpl();

    @Test
    void test_mapYamlToGraphQLApiConfig_with_test_yaml_returns_correctly_mapped_GraphQlApiConfig_object(){
        //Arrange
        String configYaml = TestUtils.getResourceFileAsString("yaml/application-sbg-test.yaml");

        //Act
        GraphQLApiConfig result = classUnderTest.mapYamlToGraphQLApiConfig(configYaml);

        //Assert
        assertThat(result).isEqualToComparingFieldByFieldRecursively(getCorrectGraphQLApiConfig());
    }
}