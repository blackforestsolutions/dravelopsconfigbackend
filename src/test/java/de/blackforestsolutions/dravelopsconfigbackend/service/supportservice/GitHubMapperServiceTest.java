package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice.GitHubMapperService;
import de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice.GitHubMapperServiceImpl;
import de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsJsonMapper;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GitHubFileRequestObjectMother.getCorrectGitHubFileRequest;
import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQLApiConfigObjectMother.getGraphQLApiConfigWithNoEmptyFields;
import static de.blackforestsolutions.dravelopsdatamodel.testutil.TestUtils.getResourceFileAsString;
import static org.assertj.core.api.Assertions.assertThat;

public class GitHubMapperServiceTest {

    private final GitHubMapperService classUnderTest = new GitHubMapperServiceImpl();

    @Test
    void test_extractGraphQlApiConfigFrom_githubFileResponse_json_returns_correct_graphQlApiConfig() throws IOException {
        String testJson = getResourceFileAsString("json/gitHubFileResponse.json");

        GraphQLApiConfig result = classUnderTest.extractGraphQlApiConfigFrom(testJson);

        assertThat(result).isEqualToComparingFieldByFieldRecursively(getGraphQLApiConfigWithNoEmptyFields());
    }

    @Test
    void test_extractGitHubFileRequestFrom_with_correct_GraphQlApiConfig_returns_correct_gitHubFileRequest() throws IOException {
        GraphQLApiConfig testApiConfig = getGraphQLApiConfigWithNoEmptyFields();

        GitHubFileRequest result = classUnderTest.extractGitHubFileRequestFrom(testApiConfig);

        assertThat(result).isEqualToIgnoringGivenFields(getCorrectGitHubFileRequest(), "message");
    }

    @Test
    void test_map_correct_GraphQlApiConfig_to_GitHubFileRequest_and_GitHubFileRequest_to_GraphQlApiConfig_returns_same_GraphQlApiConfig() throws IOException {
        GraphQLApiConfig testApiConfig = getGraphQLApiConfigWithNoEmptyFields();

        GitHubFileRequest mappedRequest = classUnderTest.extractGitHubFileRequestFrom(testApiConfig);
        GraphQLApiConfig result = classUnderTest.extractGraphQlApiConfigFrom(new DravelOpsJsonMapper().writeValueAsString(mappedRequest));

        assertThat(result).isEqualToComparingFieldByFieldRecursively(testApiConfig);
    }
}