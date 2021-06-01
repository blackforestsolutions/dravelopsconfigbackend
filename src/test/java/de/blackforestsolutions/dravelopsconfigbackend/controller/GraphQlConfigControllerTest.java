package de.blackforestsolutions.dravelopsconfigbackend.controller;

import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.GraphQlConfigIntegrationService;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.GraphQlConfigIntegrationServiceImpl;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQLApiConfigObjectMother.getGraphQLApiConfigWithNoEmptyFields;
import static de.blackforestsolutions.dravelopsdatamodel.objectmothers.CallStatusObjectMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GraphQlConfigControllerTest {

    private final GraphQlConfigIntegrationService integrationService = mock(GraphQlConfigIntegrationServiceImpl.class);

    private final GraphQlConfigController classUnderTest = new GraphQlConfigController(integrationService);

    @BeforeEach
    void init() {
        when(integrationService.getGraphQlApiConfig()).thenReturn(new ResponseEntity<>(getGraphQLApiConfigWithNoEmptyFields(), HttpStatus.OK));

        List<CallStatus<GraphQlTab>> list = List.of(getSuccessfulAddressAutocompletionCallStatusTab(),
                getFailedJourneyCallStatusTab(),
                getSuccessfulNearestAddressesCallStatusTab(),
                getSuccessfulNearestStationsCallStatusTab());

        when(integrationService.putGraphQlApiConfig(any(GraphQLApiConfig.class))).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));
    }

    @Test
    void test_getGraphQlApiConfig() {
        ResponseEntity<GraphQLApiConfig> result = classUnderTest.getGraphQlApiConfig();

        verify(integrationService, times(1)).getGraphQlApiConfig();
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualToComparingFieldByFieldRecursively(getGraphQLApiConfigWithNoEmptyFields());
    }

    @Test
    void test_putGraphQlApiConfig() {
        GraphQLApiConfig config = getGraphQLApiConfigWithNoEmptyFields();
        ArgumentCaptor<GraphQLApiConfig> configArg = ArgumentCaptor.forClass(GraphQLApiConfig.class);

        ResponseEntity<List<CallStatus<GraphQlTab>>> result = classUnderTest.putGraphQlApiConfig(config);

        verify(integrationService, times(1)).putGraphQlApiConfig(configArg.capture());
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotEmpty();
        assertThat(configArg.getValue()).isEqualToComparingFieldByFieldRecursively(config);
    }
}