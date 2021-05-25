package de.blackforestsolutions.dravelopsconfigbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQLApiConfigObjectMother;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.GitHubApiService;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.Status;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GitHubApiServiceIT{

    @Autowired
    private GitHubApiService classUnderTest;

    @Test
    void test_getGraphQlApiConfig_from_gitHub_with_correct_api_token_is_equal_to_local_mother_config() {
        CallStatus<GraphQLApiConfig> status = classUnderTest.getGraphQlApiConfig();

        assertThat(status.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(status.getThrowable()).isNull();
        assertThat(status.getCalledObject().getSha()).isNotEmpty();
        assertThat(status.getCalledObject().getGraphql()).isNotNull();
    }

    @Test
    void test_putGraphQlApiConfig_to_gitHub_with_correct_api_token_() throws JsonProcessingException {
        GraphQLApiConfig motherObject = GraphQLApiConfigObjectMother.getGraphQLApiConfigWithNoEmptyFields();
        motherObject.getGraphql().getPlayground().getSettings().getEditor().setTheme("light");

        CallStatus<String> callStatus = classUnderTest.putGraphQlApiConfig(motherObject);

        assertThat(callStatus.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(callStatus.getThrowable()).isNull();
        assertThat(callStatus.getCalledObject()).isNotEmpty();
    }
}