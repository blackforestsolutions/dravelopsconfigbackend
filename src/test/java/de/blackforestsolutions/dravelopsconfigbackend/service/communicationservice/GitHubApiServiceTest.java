package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice.GitHubMapperService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.GitHubHttpCallBuilderService;
import de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.ApiTokenObjectMother;
import de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GitHubFileRequestObjectMother;
import de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.GraphQLApiConfigObjectMother;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.Status;
import de.blackforestsolutions.dravelopsdatamodel.testutil.TestUtils;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GitHubApiServiceTest{

    private final GitHubHttpCallBuilderService callBuilderService = mock(GitHubHttpCallBuilderService.class);
    private final CallService callService = mock(CallService.class);
    private final GitHubMapperService mapperService = mock(GitHubMapperService.class);
    private final ApiToken token = ApiTokenObjectMother.getCorrectApiToken();

    private final GitHubApiService classUnderTest = new GitHubApiServiceImpl(callBuilderService, callService, mapperService, token);

    @BeforeEach
    void init() throws IOException {
        when(callBuilderService.buildGitHubHttpHeaderWith(any(ApiToken.class))).thenReturn(new HttpHeaders());

        String mappedResponseString = TestUtils.getResourceFileAsString("json/gitHubFileResponse.json");
        when(callService.get(anyString(), any(HttpEntity.class))).thenReturn(new ResponseEntity<>(mappedResponseString, HttpStatus.OK));

        String mappedRequestString = TestUtils.getResourceFileAsString("json/gitHubFileRequest.json");
        when(callService.put(anyString(), any(HttpEntity.class))).thenReturn(new ResponseEntity<>(mappedRequestString, HttpStatus.OK));

        when(callBuilderService.buildGitHubPathWith(any(ApiToken.class))).thenReturn("");

        when(mapperService.extractGitHubFileRequestFrom(any(GraphQLApiConfig.class))).thenReturn(GitHubFileRequestObjectMother.getCorrectGitHubFileRequest());

        when(mapperService.extractGraphQlApiConfigFrom(anyString())).thenReturn(GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig());
    }

    @Test
    void test_getGraphQlApiConfig_should_return_correct_CallStatus() {
        CallStatus<GraphQLApiConfig> result = classUnderTest.getGraphQlApiConfig();

        assertThat(result.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(result.getThrowable()).isNull();
        assertThat(result.getCalledObject()).isEqualToComparingFieldByFieldRecursively(GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig());
    }

    @Test
    void test_getGraphQlApiConfig_from_GitHub() throws IOException {
        ArgumentCaptor<ApiToken> tokenArg = ArgumentCaptor.forClass(ApiToken.class);
        ArgumentCaptor<String> urlArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpEntity<ApiToken>> httpEntityArg = ArgumentCaptor.forClass(HttpEntity.class);
        ArgumentCaptor<String> jsonBodyArg = ArgumentCaptor.forClass(String.class);

        classUnderTest.getGraphQlApiConfig();

        InOrder order = inOrder(callBuilderService, callService, mapperService);
        order.verify(callBuilderService, times(1)).buildGitHubHttpHeaderWith(tokenArg.capture());
        order.verify(callService, times(1)).get(urlArg.capture(), httpEntityArg.capture());
        order.verify(mapperService, times(1)).extractGraphQlApiConfigFrom(jsonBodyArg.capture());
        order.verifyNoMoreInteractions();
        assertThat(tokenArg.getValue()).isEqualToComparingFieldByFieldRecursively(ApiTokenObjectMother.getCorrectApiToken());
        assertThat(urlArg.getValue()).isEqualTo("https://api.github.com");
        assertThat(httpEntityArg.getValue()).isEqualToComparingFieldByFieldRecursively(new HttpEntity<>(new HttpHeaders()));
        assertThat(jsonBodyArg.getValue()).isEqualTo(TestUtils.getResourceFileAsString("json/gitHubFileResponse.json"));
    }

    @Test
    void test_getGraphQlApiConfig_and_thrown_exception_by_GitHubHttpCallBuilderService_returns_failed_callStatus() {
        when(callBuilderService.buildGitHubHttpHeaderWith(any(ApiToken.class))).thenThrow(NullPointerException.class);

        CallStatus<GraphQLApiConfig> result = classUnderTest.getGraphQlApiConfig();

        assertThat(result.getStatus()).isEqualTo(Status.FAILED);
        assertThat(result.getThrowable()).isInstanceOf(NullPointerException.class);
        assertThat(result.getCalledObject()).isNull();
    }

    @Test
    void test_getGraphQlApiConfig_and_thrown_exception_by_CallService_returns_failed_callStatus() {
        when(callService.get(anyString(), any(HttpEntity.class))).thenThrow(NullPointerException.class);

        CallStatus<GraphQLApiConfig> result = classUnderTest.getGraphQlApiConfig();

        assertThat(result.getStatus()).isEqualTo(Status.FAILED);
        assertThat(result.getThrowable()).isInstanceOf(NullPointerException.class);
        assertThat(result.getCalledObject()).isNull();
    }

    @Test
    void test_getGraphQlApiConfig_and_thrown_exception_by_GitHubMapperService_returns_failed_callStatus() throws IOException {
        when(mapperService.extractGraphQlApiConfigFrom(anyString())).thenThrow(NullPointerException.class);

        CallStatus<GraphQLApiConfig> result = classUnderTest.getGraphQlApiConfig();

        assertThat(result.getStatus()).isEqualTo(Status.FAILED);
        assertThat(result.getThrowable()).isInstanceOf(NullPointerException.class);
        assertThat(result.getCalledObject()).isNull();
    }


    @Test
    void test_putGraphQlApiConfig_should_return_correct_CallStatus() throws JsonProcessingException {
        GraphQLApiConfig apiConfig = GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig();

        CallStatus<String> result = classUnderTest.putGraphQlApiConfig(apiConfig);

        assertThat(result.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(result.getThrowable()).isNull();
        assertThat(result.getCalledObject()).isNotEmpty();
    }

    @Test
    void test_put_graphQlApiConfig_update_to_GitHub() throws IOException {
        GraphQLApiConfig config = GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig();
        ArgumentCaptor<ApiToken> tokenArg = ArgumentCaptor.forClass(ApiToken.class);
        ArgumentCaptor<String> jsonBodyArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> urlArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpEntity<GitHubFileRequest>> httpEntityArg = ArgumentCaptor.forClass(HttpEntity.class);

        classUnderTest.putGraphQlApiConfig(config);

        InOrder order = inOrder(callBuilderService, callService, mapperService);
        order.verify(mapperService, times(1)).extractGitHubFileRequestFrom(config);
        order.verify(callBuilderService, times(1)).buildGitHubHttpHeaderWith(tokenArg.capture());
        order.verify(callService, times(1)).get(urlArg.capture(), httpEntityArg.capture());
        order.verify(mapperService, times(1)).extractGraphQlApiConfigFrom(jsonBodyArg.capture());
        order.verify(callBuilderService, times(1)).buildGitHubHttpHeaderWith(tokenArg.capture());
        order.verify(callService, times(1)).put(urlArg.capture(), httpEntityArg.capture());
        order.verifyNoMoreInteractions();
        assertThat(tokenArg.getValue()).isEqualToComparingFieldByFieldRecursively(ApiTokenObjectMother.getCorrectApiToken());
        assertThat(urlArg.getValue()).isEqualTo("https://api.github.com");
        assertThat(httpEntityArg.getValue()).isEqualToComparingFieldByFieldRecursively(
                new HttpEntity<>(GitHubFileRequestObjectMother.getCorrectGitHubFileRequest(), new HttpHeaders()));
        assertThat(jsonBodyArg.getValue()).isEqualTo(TestUtils.getResourceFileAsString("json/gitHubFileResponse.json"));
    }

    @Test
    void test_putGraphQlApiConfig_and_thrown_exception_by_GitHubHttpCallBuilderService_returns_failed_callStatus() throws JsonProcessingException {
        when(callBuilderService.buildGitHubHttpHeaderWith(any(ApiToken.class))).thenThrow(NullPointerException.class);
        GraphQLApiConfig config = GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig();

        CallStatus<String> result = classUnderTest.putGraphQlApiConfig(config);

        assertThat(result.getStatus()).isEqualTo(Status.FAILED);
        assertThat(result.getThrowable()).isInstanceOf(NullPointerException.class);
        assertThat(result.getCalledObject()).isNull();
    }

    @Test
    void test_putGraphQlApiConfig_and_thrown_exception_by_GitHubMapperService_returns_failed_callStatus() throws JsonProcessingException {
        when(mapperService.extractGitHubFileRequestFrom(any(GraphQLApiConfig.class))).thenThrow(NullPointerException.class);
        GraphQLApiConfig config = GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig();

        CallStatus<String> result = classUnderTest.putGraphQlApiConfig(config);

        assertThat(result.getStatus()).isEqualTo(Status.FAILED);
        assertThat(result.getThrowable()).isInstanceOf(NullPointerException.class);
        assertThat(result.getCalledObject()).isNull();
    }

    @Test
    void test_putGraphQlApiConfig_and_thrown_exception_by_CallService_put_returns_failed_callStatus() throws JsonProcessingException {
        when(callService.put(anyString(), any(HttpEntity.class))).thenThrow(NullPointerException.class);
        GraphQLApiConfig config = GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig();

        CallStatus<String> result = classUnderTest.putGraphQlApiConfig(config);

        assertThat(result.getStatus()).isEqualTo(Status.FAILED);
        assertThat(result.getThrowable()).isInstanceOf(NullPointerException.class);
        assertThat(result.getCalledObject()).isNull();
    }

    @Test
    void test_putGraphQlApiConfig_and_thrown_exception_by_CallService_get_returns_failed_callStatus() throws JsonProcessingException {
        when(callService.get(anyString(), any(HttpEntity.class))).thenThrow(NullPointerException.class);
        GraphQLApiConfig config = GraphQLApiConfigObjectMother.getCorrectGraphQLApiConfig();

        CallStatus<String> result = classUnderTest.putGraphQlApiConfig(config);

        assertThat(result.getStatus()).isEqualTo(Status.FAILED);
        assertThat(result.getThrowable()).isInstanceOf(NullPointerException.class);
        assertThat(result.getCalledObject()).isNull();
    }

    @Test
    void test_putGraphQlApiConfig_with_null_GraphQlApiConfig_returns_failed_callStatus() throws JsonProcessingException {
        CallStatus<String> result = classUnderTest.putGraphQlApiConfig(null);

        assertThat(result.getStatus()).isEqualTo(Status.FAILED);
        assertThat(result.getThrowable()).isInstanceOf(NullPointerException.class);
        assertThat(result.getCalledObject()).isNull();
    }
}