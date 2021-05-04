package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsconfigbackend.model.GraphQLApiConfig;
import de.blackforestsolutions.dravelopsdatamodel.testutil.TestUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileMapperServiceTest{

    private final FileMapperService classUnderTest = new FileMapperServiceImpl();


    @Test
    void test_mapYamlWith_test_yaml_file_to_GraphQLApiConfig_returns_correctly_mapped_GraphQlApiConfig_object() throws IOException {
        File yamlFile = TestUtils.getResourceAsFile("test.files/application-sbg-test.yaml", "");

        GraphQLApiConfig result = classUnderTest.mapYamlWith(yamlFile, GraphQLApiConfig.class);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(getCorrectGraphQLApiConfig());
    }

    @Test
    void test_mapYamlWith_incorrect_test_yaml_file_to_GraphQLApiConfig_returns_incorrectly_mapped_GraphQlApiConfig_object() throws IOException {

        File yamlFile = TestUtils.getResourceAsFile("test.files/application-sbg-test-wrong.yaml", "");

        GraphQLApiConfig result = classUnderTest.mapYamlWith(yamlFile, GraphQLApiConfig.class);

        assertThat(result).isNotEqualTo(getCorrectGraphQLApiConfig());
    }

    @Test
    void test_mapYamlWith_null_test_yaml_file_returns_exception() {
        assertThrows(NullPointerException.class, () -> classUnderTest.mapYamlWith(null, GraphQLApiConfig.class));
    }


    @Test
    void test_mapJsonWith_test_json_file_to_GraphQLApiConfig_returns_correctly_mapped_GraphQlApiConfig_object() throws IOException {
        File jsonFile = TestUtils.getResourceAsFile("test.files/application-sbg-test.json", "");

        GraphQLApiConfig result = classUnderTest.mapJsonWith(jsonFile, GraphQLApiConfig.class);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(getCorrectGraphQLApiConfig());
    }

    @Test
    void test_mapJsonWith_incorrect_test_json_file_to_GraphQLApiConfig_returns_incorrectly_mapped_GraphQlApiConfig_object() throws IOException {
        File jsonFile = TestUtils.getResourceAsFile("test.files/application-sbg-test-wrong.json", "");

        GraphQLApiConfig result = classUnderTest.mapJsonWith(jsonFile, GraphQLApiConfig.class);

        assertThat(result).isNotEqualTo(getCorrectGraphQLApiConfig());
    }

    @Test
    void test_mapJsonWith_null_test_json_file_returns_exception() {
        assertThrows(NullPointerException.class, () -> classUnderTest.mapJsonWith(null, GraphQLApiConfig.class));
    }


    @Test
    void test_mapObjectToYaml_with_test_yaml_file_returns_correct_file() throws IOException {
        File yamlFile = TestUtils.getResourceAsFile("test.files/application-sbg-test.yaml", "");

        File result = classUnderTest.mapObjectToYaml(getCorrectGraphQLApiConfig());

        assertThat(Files.readString(Path.of(yamlFile.getPath())))
                .isEqualToIgnoringWhitespace(Files.readString(Path.of(result.getPath())));
    }

    @Test
    void test_mapObjectToYaml_with_GraphQlApiConfig_to_test_yaml_file_returns_correct_file() throws IOException {
        File yamlFile = TestUtils.getResourceAsFile("test.files/application-sbg-test.yaml", "");

        File result = classUnderTest.mapObjectToYaml(getCorrectGraphQLApiConfig());

        assertThat(Files.readString(Path.of(yamlFile.getPath())))
                .isEqualToIgnoringWhitespace(Files.readString(Path.of(result.getPath())));
    }

    @Test
    void test_mapObjectToYaml_with_GraphQlApiConfig_to_incorrect_test_yaml_file_returns_incorrect_file() throws IOException {
        File yamlFile = TestUtils.getResourceAsFile("test.files/application-sbg-test-wrong.yaml", "");

        File result = classUnderTest.mapObjectToYaml(getCorrectGraphQLApiConfig());

        assertThat(Files.readString(Path.of(yamlFile.getPath())))
                .isNotEqualToIgnoringWhitespace(Files.readString(Path.of(result.getPath())));
    }

    @Test
    void test_mapObjectToYaml_with_null_returns_nullPointerException() {
        assertThrows(NullPointerException.class, () -> classUnderTest.mapObjectToYaml(null));
    }


    @Test
    void test_mapObjectToJson_with_GraphQlApiConfig_to_test_json_file_returns_correct_file() throws IOException {
        File jsonFile = TestUtils.getResourceAsFile("test.files/application-sbg-test.json", "");

        File result = classUnderTest.mapObjectToJson(getCorrectGraphQLApiConfig());

        assertThat(Files.readString(Path.of(jsonFile.getPath())))
                .isEqualToIgnoringWhitespace(Files.readString(Path.of(result.getPath())));
    }

    @Test
    void test_mapObjectToJson_with_GraphQlApiConfig_to_incorrect_test_json_file_returns_incorrect_file() throws IOException {
        File jsonFile = TestUtils.getResourceAsFile("test.files/application-sbg-test-wrong.json", "");

        File result = classUnderTest.mapObjectToJson(getCorrectGraphQLApiConfig());

        assertThat(Files.readString(Path.of(jsonFile.getPath())))
                .isNotEqualToIgnoringWhitespace(Files.readString(Path.of(result.getPath())));
    }

    @Test
    void test_mapObjectToJson_with_null_returns_nullPointerException() {
        assertThrows(NullPointerException.class, () -> classUnderTest.mapObjectToJson(null));
    }
}