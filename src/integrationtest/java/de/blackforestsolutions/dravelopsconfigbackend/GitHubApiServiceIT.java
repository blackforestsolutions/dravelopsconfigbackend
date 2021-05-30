package de.blackforestsolutions.dravelopsconfigbackend;

import de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQLApiConfigObjectMother;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.GitHubApiService;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GitHubApiServiceIT{

    @Autowired
    private GitHubApiService classUnderTest;

    @Test
    void test_getGraphQlApiConfig_from_gitHub_with_correct_api_token_is_equal_to_local_mother_config() throws IOException {
        GraphQLApiConfig result = classUnderTest.getGraphQlApiConfig();

        assertThat(result).isNotNull();
        assertThat(result.getSha()).isNotEmpty();
        assertThat(result.getGraphql()).isNotNull();
    }

    @Test
    void test_putGraphQlApiConfig_to_gitHub_with_correct_api_token_() throws IOException {
        GraphQLApiConfig motherObject = GraphQLApiConfigObjectMother.getGraphQLApiConfigWithNoEmptyFields();
        motherObject.getGraphql().getPlayground().getSettings().getEditor().setTheme("light");

        classUnderTest.putGraphQlApiConfig(motherObject);
    }
}