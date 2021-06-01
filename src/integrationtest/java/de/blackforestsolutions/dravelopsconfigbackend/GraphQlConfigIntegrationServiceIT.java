package de.blackforestsolutions.dravelopsconfigbackend;

import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.GraphQlConfigIntegrationService;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQLApiConfigObjectMother.getGraphQLApiConfigWithNoEmptyFields;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GraphQlConfigIntegrationServiceIT {

    @Autowired
    private GraphQlConfigIntegrationService classUnderTest;

    @Test
    void test_getGraphQlApiConfig() {
        ResponseEntity<GraphQLApiConfig> result = classUnderTest.getGraphQlApiConfig();

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
    }

    @Test
    void test_putGraphQlApiConfig() {
        GraphQLApiConfig apiConfig = getGraphQLApiConfigWithNoEmptyFields();

        ResponseEntity<List<CallStatus<GraphQlTab>>> result = classUnderTest.putGraphQlApiConfig(apiConfig);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isNotEmpty();
        assertThat(result.getBody()).allMatch(graphQlTab -> graphQlTab.getStatus() != null);
    }
}
