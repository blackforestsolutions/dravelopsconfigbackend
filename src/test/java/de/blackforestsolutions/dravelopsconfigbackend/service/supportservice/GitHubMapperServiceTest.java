package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import com.hazelcast.com.google.common.io.Files;
import de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice.GitHubMapperService;
import de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice.GitHubMapperServiceImpl;
import de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GitHubFileRequestObjectMother;
import de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GraphQLApiConfigObjectMother;
import de.blackforestsolutions.dravelopsdatamodel.testutil.TestUtils;
import de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsJsonMapper;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GitHubMapperServiceTest{

    private final GitHubMapperService classUnderTest = new GitHubMapperServiceImpl();

    @Test
    void test_map_correct_response_to_GraphQlApiConfig() throws IOException {
        File localFile = TestUtils.getResourceAsFile("test.files/json/gitHubFileResponse.json", "");

        String jsonBody = new String(Files.toByteArray(localFile));
        GraphQLApiConfig graphQLApiConfig = GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig();
        GraphQLApiConfig createdGraphQLApiConfig = classUnderTest.mapToGraphQlApiConfigWith(jsonBody);

        assertThat(graphQLApiConfig).isEqualToComparingFieldByFieldRecursively(createdGraphQLApiConfig);
    }

    @Test
    void test_mapToGitHubFile_with_correct_GraphQlApiConfig_returns_correct_GitHubFileRequest() throws IOException {
        GitHubFileRequest mappedRequest = classUnderTest.mapToGitHubFileRequestWith(GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig());
        mappedRequest.setMessage("file test 2");

        GitHubFileRequest objectMotherGitHubFileRequest = GitHubFileRequestObjectMother.getCorrectGitHubFileRequest();

        assertThat(mappedRequest).isEqualToComparingFieldByFieldRecursively(objectMotherGitHubFileRequest);
    }

    @Test
    void test_map_correct_GraphQlApiConfig_to_GitHubFileRequest_and_GitHubFileRequest_to_GraphQlApiConfig_returns_same_GraphQlApiConfig() throws IOException {
        GitHubFileRequest mappedRequest = classUnderTest.mapToGitHubFileRequestWith(GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig());
        mappedRequest.setMessage("file test 2");
        GraphQLApiConfig createdGraphQLApiConfig = classUnderTest.mapToGraphQlApiConfigWith(new DravelOpsJsonMapper().writeValueAsString(mappedRequest));

        assertThat(GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig()).isEqualToComparingFieldByFieldRecursively(createdGraphQLApiConfig);
    }
}