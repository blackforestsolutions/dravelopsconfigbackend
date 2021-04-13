package de.blackforestsolutions.dravelopsconfigbackend;

import de.blackforestsolutions.dravelopsconfigbackend.model.GraphQLApiConfig;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.git.GitService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.FileMapperService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.GitCallBuilderService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.testutil.TestUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(ImportApiToken.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GitServiceIT {

    private static final String REPOSITORY_LINK = "https://github.com/Luca1235/TestDeployment";
    private static final String FILE_PATH = "projects/sbg";
    private static final String FILE_NAME = "application-sbg.yaml";
    private static final String USERNAME = "Luca1235";
    private static final String PASSWORD_TOKEN = "f186e448c9c80c242f4c7ddd8e10182196c8a261";

    @Autowired
    private GitService gitService;
    @Autowired
    private FileMapperService fileMapperService;
    @Autowired
    private GitCallBuilderService gitCallBuilderService;
    @Autowired
    private ApiToken correctApiToken;
    @Autowired
    private ApiToken incorrectApiToken;

    @Test
    void test_does_git_call_builder_work() {
        String path = REPOSITORY_LINK
                .concat("/")
                .concat(FILE_PATH)
                .concat("/")
                .concat(FILE_NAME);

        assertThat(gitCallBuilderService.buildGitUrlWith(correctApiToken)).isEqualTo(path);
    }



    @Test
    void test_mapYamlToGraphQLApiConfig_with_test_yaml_file_returns_correctly_mapped_GraphQlApiConfig_object() throws IOException {
        //Arrange
        File yamlFile = TestUtils.getResourceAsFile("test.files/application-sbg-test.yaml", "");
        //Access
        GraphQLApiConfig result = fileMapperService.mapYamlWith(yamlFile, GraphQLApiConfig.class);
        //Assert
        assertThat(result).isEqualToComparingFieldByFieldRecursively(getCorrectGraphQLApiConfig());
    }

    @Test
    void test_mapYamlToGraphQLApiConfig_with_wrong_test_yaml_file_returns_wrongly_mapped_GraphQlApiConfig_object() throws IOException {
        //Arrange
        File yamlFile = TestUtils.getResourceAsFile("test.files/application-sbg-test-wrong.yaml", "");
        //Access
        GraphQLApiConfig result = fileMapperService.mapYamlWith(yamlFile, GraphQLApiConfig.class);
        //Assert
        assertThat(result).isNotEqualTo(getCorrectGraphQLApiConfig());
    }

    @Test
    void test_mapYamlToGraphQLApiConfig_with_null_test_yaml_file_returns_exception(){
        //Arrange
        File yamlFile = null;
        //Access Assert
        assertThrows(NullPointerException.class, () -> fileMapperService.mapYamlWith(yamlFile, GraphQLApiConfig.class));
    }



    @Test
    void test_mapJsonToGraphQLApiConfig_with_test_json_file_returns_correctly_mapped_GraphQlApiConfig_object() throws IOException {
        //Arrange
        File jsonFile = TestUtils.getResourceAsFile("test.files/application-sbg-test.json", "");
        //Access
        GraphQLApiConfig result = fileMapperService.mapJsonWith(jsonFile, GraphQLApiConfig.class);
        //Assert
        assertThat(result).isEqualToComparingFieldByFieldRecursively(getCorrectGraphQLApiConfig());
    }

    @Test
    void test_mapJsonToGraphQLApiConfig_with_wrong_test_json_file_returns_wrongly_mapped_GraphQlApiConfig_object() throws IOException {
        //Arrange
        File jsonFile = TestUtils.getResourceAsFile("test.files/application-sbg-test-wrong.json", "");
        //Access
        GraphQLApiConfig result = fileMapperService.mapJsonWith(jsonFile, GraphQLApiConfig.class);
        //Assert
        assertThat(result).isNotEqualTo(getCorrectGraphQLApiConfig());
    }

    @Test
    void test_mapJsonToGraphQLApiConfig_with_null_test_json_file_returns_exception(){
        //Arrange
        File jsonFile = null;
        //Access|Assert
        assertThrows(NullPointerException.class, () -> fileMapperService.mapJsonWith(jsonFile, GraphQLApiConfig.class));
    }



    @Test
    void test_pull_from_git_hub_with_correct_api_token_files_are_the_same() throws GitAPIException, IOException {
        //Arrange
        File jsonFile = TestUtils.getResourceAsFile("test.files/application-sbg-test.json", "");
        //Access
        File fetchedFile = gitService.pullFileFromGitWith(correctApiToken);
        //Assert
        assertThat(Files.readString(Path.of(jsonFile.getPath())))
                .isEqualToIgnoringWhitespace(Files.readString(Path.of(fetchedFile.getPath())));
    }

    @Test
    void test_pull_from_git_hub_with_correct_api_token_files_are_not_the_same() throws GitAPIException, IOException {
        //Arrange
        File jsonFile = TestUtils.getResourceAsFile("test.files/application-sbg-test-wrong.json", "");
        //Access
        File fetchedFile = gitService.pullFileFromGitWith(correctApiToken);
        //Assert
        assertThat(Files.readString(Path.of(jsonFile.getPath())))
                .isNotEqualToIgnoringWhitespace(Files.readString(Path.of(fetchedFile.getPath())));
    }

    @Test
    void test_pull_from_git_hub_with_incorrect_api_token_password_throws_exception() {
        //Assert
        assertThrows(TransportException.class, () -> gitService.pullFileFromGitWith(incorrectApiToken));
    }

}