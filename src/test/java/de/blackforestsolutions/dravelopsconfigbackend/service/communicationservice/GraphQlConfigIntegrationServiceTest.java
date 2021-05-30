package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.ExceptionHandlerService;
import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.ExceptionHandlerServiceImpl;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQLApiConfigObjectMother.getGraphQLApiConfigWithNoEmptyFields;
import static de.blackforestsolutions.dravelopsdatamodel.objectmothers.CallStatusObjectMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GraphQlConfigIntegrationServiceTest {

    private final GitHubApiService gitHubApiService = mock(GitHubApiServiceImpl.class);
    private final RefreshConfigService refreshConfigService = mock(RefreshConfigServiceImpl.class);
    private final TestSoftwareService testSoftwareService = mock(TestSoftwareServiceImpl.class);
    private final ExceptionHandlerService exceptionHandlerService = new ExceptionHandlerServiceImpl();

    private final GraphQlConfigIntegrationService classUnderTest = new GraphQlConfigIntegrationServiceImpl(
            gitHubApiService,
            refreshConfigService,
            testSoftwareService,
            exceptionHandlerService
    );

    @BeforeEach
    void init() throws IOException {
        when(gitHubApiService.getGraphQlApiConfig()).thenReturn(getGraphQLApiConfigWithNoEmptyFields());

        List<CallStatus<GraphQlTab>> statusList = List.of(getSuccessfulAddressAutocompletionCallStatusTab(), getSuccessfulNearestAddressesCallStatusTab());
        when(testSoftwareService.getTestSoftwareResultWith(any(GraphQLApiConfig.class))).thenReturn(statusList);
    }

    @Test
    void test_getGraphQlApiConfig_with_successful_CallStatus_returns_GraphQlApiConfig_ResponseEntity_with_HttpStatus_OK() throws IOException {
        ResponseEntity<GraphQLApiConfig> result = classUnderTest.getGraphQlApiConfig();

        verify(gitHubApiService, times(1)).getGraphQlApiConfig();
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody()).isEqualToComparingFieldByFieldRecursively(getGraphQLApiConfigWithNoEmptyFields());
    }

    @Test
    void test_getGraphQlApiConfig_passes_empty_ResponseEntity_when_gitHubApiService_throws_exception() throws IOException {
        when(gitHubApiService.getGraphQlApiConfig()).thenThrow(NullPointerException.class);

        ResponseEntity<GraphQLApiConfig> result = classUnderTest.getGraphQlApiConfig();

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isNull();
    }

    @Test
    void test_putGraphQlApiConfig_with_successful_CallStatuses_returns_ResponseEntity_with_HttpStatus_OK() throws IOException {
        GraphQLApiConfig config = getGraphQLApiConfigWithNoEmptyFields();
        ArgumentCaptor<GraphQLApiConfig> configArg = ArgumentCaptor.forClass(GraphQLApiConfig.class);

        ResponseEntity<List<CallStatus<GraphQlTab>>> result = classUnderTest.putGraphQlApiConfig(config);

        InOrder order = inOrder(testSoftwareService, gitHubApiService, refreshConfigService);
        order.verify(testSoftwareService, times(1)).getTestSoftwareResultWith(configArg.capture());
        order.verify(gitHubApiService, times(1)).putGraphQlApiConfig(configArg.capture());
        order.verify(refreshConfigService, times(1)).refreshConfigs();
        order.verifyNoMoreInteractions();

        assertThat(result.getBody()).isNotEmpty();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void test_putGraphQlApiConfig_with_single_failed_CallStatus_returns_GraphQlTabList_with_HttpStatus_OK() {
        List<CallStatus<GraphQlTab>> statusList = List.of(getSuccessfulAddressAutocompletionCallStatusTab(), getFailedAddressAutocompletionCallStatusTab());
        when(testSoftwareService.getTestSoftwareResultWith(any(GraphQLApiConfig.class))).thenReturn(statusList);
        GraphQLApiConfig config = getGraphQLApiConfigWithNoEmptyFields();
        ArgumentCaptor<GraphQLApiConfig> configArg = ArgumentCaptor.forClass(GraphQLApiConfig.class);

        ResponseEntity<List<CallStatus<GraphQlTab>>> result = classUnderTest.putGraphQlApiConfig(config);

        InOrder order = inOrder(testSoftwareService, gitHubApiService, refreshConfigService);
        order.verify(testSoftwareService, times(1)).getTestSoftwareResultWith(configArg.capture());
        order.verifyNoMoreInteractions();
        assertThat(result.getBody()).isNotEmpty();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void test_putGraphQlApiConfig_with_null_GraphQlApiConfig() {
        List<CallStatus<GraphQlTab>> statusList = List.of(getSuccessfulAddressAutocompletionCallStatusTab(), getFailedAddressAutocompletionCallStatusTab());
        when(testSoftwareService.getTestSoftwareResultWith(any(GraphQLApiConfig.class))).thenReturn(statusList);

        ResponseEntity<List<CallStatus<GraphQlTab>>> result = classUnderTest.putGraphQlApiConfig(null);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEmpty();
    }

    @Test
    void test_putGraphQlApiConfig_passes_empty_ResponseEntity_when_testSoftwareService_throws_exception() {
        when(testSoftwareService.getTestSoftwareResultWith(any(GraphQLApiConfig.class))).thenThrow(NullPointerException.class);

        ResponseEntity<List<CallStatus<GraphQlTab>>> result = classUnderTest.putGraphQlApiConfig(getGraphQLApiConfigWithNoEmptyFields());

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEmpty();
    }
}