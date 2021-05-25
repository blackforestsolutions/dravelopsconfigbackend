package de.blackforestsolutions.dravelopsconfigbackend;

import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.TestSoftwareHttpCallBuilderService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Map;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQLApiConfigObjectMother.getGraphQLApiConfigWithNoEmptyFields;
import static de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsHttpCallBuilder.buildUrlWith;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TestSoftwareCallServiceIT {

    @Autowired
    private CallService classUnderTest;

    @Autowired
    private TestSoftwareHttpCallBuilderService testSoftwareHttpCallBuilderService;

    @Autowired
    private ApiToken testSoftwareApiToken;

    @Test
    void test_post_graphqlApiTokenMap_to_testSoftware_returns_correct_http_status_with_body_result() {
        ApiToken testData = new ApiToken(testSoftwareApiToken);
        GraphQLApiConfig testApiConfig = getGraphQLApiConfigWithNoEmptyFields();
        String testUrl = buildUrlWith(testData).toString();
        HttpEntity<Map<GraphQlTab, ApiToken>> testHttpEntity = testSoftwareHttpCallBuilderService.buildTestSoftwareHttpEntityWith(testApiConfig);

        ResponseEntity<String> result = classUnderTest.post(testUrl, testHttpEntity);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotEmpty();
    }
}
