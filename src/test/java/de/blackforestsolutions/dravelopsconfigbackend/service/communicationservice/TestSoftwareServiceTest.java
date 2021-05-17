package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.ExceptionHandlerService;
import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.ExceptionHandlerServiceImpl;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallServiceImpl;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.TestSoftwareHttpCallBuilderService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.TestSoftwareHttpCallBuilderServiceImpl;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsJsonMapper;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.ApiTokenObjectMother.getConfiguredTestSoftwareApiToken;
import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQLApiConfigObjectMother.getGraphQLApiConfigWithNoEmptyFields;
import static de.blackforestsolutions.dravelopsdatamodel.objectmothers.ApiTokenObjectMother.getTestSoftwareApiTokens;
import static de.blackforestsolutions.dravelopsdatamodel.objectmothers.CallStatusObjectMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TestSoftwareServiceTest {

    private final CallService callService = mock(CallServiceImpl.class);
    private final TestSoftwareHttpCallBuilderService testSoftwareHttpCallBuilderService = mock(TestSoftwareHttpCallBuilderServiceImpl.class);
    private final ExceptionHandlerService exceptionHandlerService = spy(ExceptionHandlerServiceImpl.class);
    private final ApiToken testSoftwareApiToken = getConfiguredTestSoftwareApiToken();

    private final TestSoftwareService classUnderTest = new TestSoftwareServiceImpl(
            callService,
            testSoftwareHttpCallBuilderService,
            exceptionHandlerService,
            testSoftwareApiToken
    );

    @BeforeEach
    void init() throws JsonProcessingException {
        DravelOpsJsonMapper mapper = new DravelOpsJsonMapper();
        List<CallStatus<GraphQlTab>> callStatuses = List.of(
                getSuccessfulJourneyCallStatusTab(),
                getSuccessfulAddressAutocompletionCallStatusTab(),
                getFailedNearestAddressesCallStatusTab(),
                getFailedNearestStationsCallStatusTab()
        );

        when(callService.post(anyString(), any(HttpEntity.class))).thenReturn(
                new ResponseEntity<>(mapper.writeValueAsString(callStatuses), HttpStatus.OK)
        );

        when(testSoftwareHttpCallBuilderService.buildTestSoftwareHttpEntityWith(any(GraphQLApiConfig.class)))
                .thenReturn(new HttpEntity<>(getTestSoftwareApiTokens()));
    }

    @Test
    void test_getTestSoftwareResultWith_graphqlApiConfig_returns_callStatus_list_with_graphqlTab() {
        GraphQLApiConfig testData = new GraphQLApiConfig();

        List<CallStatus<GraphQlTab>> result = classUnderTest.getTestSoftwareResultWith(testData);

        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get(0)).isEqualToComparingFieldByField(getSuccessfulJourneyCallStatusTab());
        assertThat(result.get(1)).isEqualToComparingFieldByField(getSuccessfulAddressAutocompletionCallStatusTab());
        assertThat(result.get(2)).isEqualToComparingFieldByField(getFailedNearestAddressesCallStatusTab());
        assertThat(result.get(3)).isEqualToComparingFieldByField(getFailedNearestStationsCallStatusTab());
    }

    @Test
    void test_getTestSoftwareResultWith_graphqlApiConfig_is_executed_correctly_with_correct_params() throws JsonProcessingException {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        ArgumentCaptor<GraphQLApiConfig> graphQLApiConfigArg = ArgumentCaptor.forClass(GraphQLApiConfig.class);
        ArgumentCaptor<String> urlArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpEntity<Map<GraphQlTab, ApiToken>>> httpEntityArg = ArgumentCaptor.forClass(HttpEntity.class);

        classUnderTest.getTestSoftwareResultWith(testData);

        InOrder inOrder = inOrder(callService, testSoftwareHttpCallBuilderService);
        inOrder.verify(testSoftwareHttpCallBuilderService, times(1)).buildTestSoftwareHttpEntityWith(graphQLApiConfigArg.capture());
        inOrder.verify(callService, times(1)).post(urlArg.capture(), httpEntityArg.capture());
        inOrder.verifyNoMoreInteractions();
        assertThat(graphQLApiConfigArg.getValue()).isEqualToComparingFieldByFieldRecursively(testData);
        assertThat(urlArg.getValue()).isEqualTo("http://localhost:8082/tests/execute");
        assertThat(httpEntityArg.getValue().getHeaders()).isEqualTo(HttpHeaders.EMPTY);
        assertThat(httpEntityArg.getValue().getBody().size()).isEqualTo(getTestSoftwareApiTokens().size());
        assertThat(httpEntityArg.getValue().getBody().get(GraphQlTab.JOURNEY_QUERY)).isEqualToComparingFieldByFieldRecursively(getTestSoftwareApiTokens().get(GraphQlTab.JOURNEY_QUERY));
        assertThat(httpEntityArg.getValue().getBody().get(GraphQlTab.ADDRESS_AUTOCOMPLETION)).isEqualToComparingFieldByFieldRecursively(getTestSoftwareApiTokens().get(GraphQlTab.ADDRESS_AUTOCOMPLETION));
        assertThat(httpEntityArg.getValue().getBody().get(GraphQlTab.NEAREST_ADDRESSES)).isEqualToComparingFieldByFieldRecursively(getTestSoftwareApiTokens().get(GraphQlTab.NEAREST_ADDRESSES));
        assertThat(httpEntityArg.getValue().getBody().get(GraphQlTab.NEAREST_STATIONS)).isEqualToComparingFieldByFieldRecursively(getTestSoftwareApiTokens().get(GraphQlTab.NEAREST_STATIONS));
    }

    @Test
    void test_getTestsoftwareResultWith_graphqlApiConfig_returns_zero_call_statuses_when_exception_is_thrown_by_httpCallBuilder() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        when(testSoftwareHttpCallBuilderService.buildTestSoftwareHttpEntityWith(any(GraphQLApiConfig.class)))
                .thenThrow(new NullPointerException());

        List<CallStatus<GraphQlTab>> result = classUnderTest.getTestSoftwareResultWith(testData);

        assertThat(result.size()).isEqualTo(0);
        verify(exceptionHandlerService, times(1)).handleExceptions(any(NullPointerException.class));
    }

    @Test
    void test_getTestsoftwareResultWith_graphqlApiConfig_returns_zero_call_statuses_when_exception_is_thrown_by_CallService() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        when(callService.post(anyString(), any(HttpEntity.class)))
                .thenThrow(new NullPointerException());

        List<CallStatus<GraphQlTab>> result = classUnderTest.getTestSoftwareResultWith(testData);

        assertThat(result.size()).isEqualTo(0);
        verify(exceptionHandlerService, times(1)).handleExceptions(any(NullPointerException.class));
    }

    @Test
    void test_getTestsoftwareResultWith_graphqlApiConfig_return_zero_call_statuses_when_response_body_is_null() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        when(callService.post(anyString(), any(HttpEntity.class))).thenReturn(
                new ResponseEntity<>(null, HttpStatus.OK)
        );

        List<CallStatus<GraphQlTab>> result = classUnderTest.getTestSoftwareResultWith(testData);

        assertThat(result.size()).isEqualTo(0);
        verify(exceptionHandlerService, times(1)).handleExceptions(any(IllegalArgumentException.class));

    }

    @Test
    void test_getTestsoftwareResultWith_graphqlApiConfig_return_zero_call_statuses_when_response_body_is_empty() {
        GraphQLApiConfig testData = getGraphQLApiConfigWithNoEmptyFields();
        when(callService.post(anyString(), any(HttpEntity.class))).thenReturn(
                new ResponseEntity<>("{}", HttpStatus.OK)
        );

        List<CallStatus<GraphQlTab>> result = classUnderTest.getTestSoftwareResultWith(testData);

        assertThat(result.size()).isEqualTo(0);
        verify(exceptionHandlerService, times(1)).handleExceptions(any(MismatchedInputException.class));

    }

}