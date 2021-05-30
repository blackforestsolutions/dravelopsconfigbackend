package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.blackforestsolutions.dravelopsconfigbackend.objectmothers.ApiTokenObjectMother;
import de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GitHubFileRequestObjectMother;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice.GitHubMapperService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.GitHubHttpCallBuilderService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
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

import static de.blackforestsolutions.dravelopsconfigbackend.configuration.TestConfiguration.*;
import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GraphQLApiConfigObjectMother.getGraphQLApiConfigWithNoEmptyFields;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        String mappedResponseString = TestUtils.getResourceFileAsString(GITHUB_FILE_RESPONSE_JSON_PATH);
        when(callService.get(anyString(), any(HttpEntity.class))).thenReturn(new ResponseEntity<>(mappedResponseString, HttpStatus.OK));

        String mappedRequestString = TestUtils.getResourceFileAsString(GITHUB_FILE_REQUEST_JSON_PATH);
        when(callService.put(anyString(), any(HttpEntity.class))).thenReturn(new ResponseEntity<>(mappedRequestString, HttpStatus.OK));

        when(callBuilderService.buildGitHubPathWith(any(ApiToken.class))).thenReturn("");

        when(mapperService.extractGitHubFileRequestFrom(any(GraphQLApiConfig.class))).thenReturn(GitHubFileRequestObjectMother.getCorrectGitHubFileRequest());

        when(mapperService.extractGraphQlApiConfigFrom(anyString())).thenReturn(getGraphQLApiConfigWithNoEmptyFields());
    }

    @Test
    void test_getGraphQlApiConfig_should_return_correct_CallStatus() throws IOException {
        GraphQLApiConfig result = classUnderTest.getGraphQlApiConfig();

        assertThat(result).isNotNull();
        assertThat(result).isEqualToComparingFieldByFieldRecursively(getGraphQLApiConfigWithNoEmptyFields());
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
        assertThat(urlArg.getValue()).isEqualTo(GITHUB_HOST_PATH);
        assertThat(httpEntityArg.getValue()).isEqualToComparingFieldByFieldRecursively(new HttpEntity<>(new HttpHeaders()));
        assertThat(jsonBodyArg.getValue()).isEqualTo(TestUtils.getResourceFileAsString(GITHUB_FILE_RESPONSE_JSON_PATH));
    }

    @Test
    void test_getGraphQlApiConfig_passes_exception_when_callBuilderService_throws_exception() {
        when(callBuilderService.buildGitHubHttpHeaderWith(any(ApiToken.class))).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, classUnderTest::getGraphQlApiConfig);
    }

    @Test
    void test_getGraphQlApiConfig_passes_exception_when_callService_throws_exception() {
        when(callService.get(anyString(), any(HttpEntity.class))).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, classUnderTest::getGraphQlApiConfig);
    }

    @Test
    void test_getGraphQlApiConfig_passes_exception_when_mapperService_throws_exception() throws IOException {
        when(mapperService.extractGraphQlApiConfigFrom(anyString())).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, classUnderTest::getGraphQlApiConfig);
    }

    @Test
    void test_put_graphQlApiConfig_update_to_GitHub() throws IOException {
        GraphQLApiConfig config = getGraphQLApiConfigWithNoEmptyFields();
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
        assertThat(urlArg.getValue()).isEqualTo(GITHUB_HOST_PATH);
        assertThat(httpEntityArg.getValue()).isEqualToComparingFieldByFieldRecursively(
                new HttpEntity<>(GitHubFileRequestObjectMother.getCorrectGitHubFileRequest(), new HttpHeaders()));
        assertThat(jsonBodyArg.getValue()).isEqualTo(TestUtils.getResourceFileAsString(GITHUB_FILE_RESPONSE_JSON_PATH));
    }

    @Test
    void test_putGraphQlApiConfig_passes_exception_when_callBuilderService_throws_exception() {
        when(callBuilderService.buildGitHubHttpHeaderWith(any(ApiToken.class))).thenThrow(NullPointerException.class);
        GraphQLApiConfig config = getGraphQLApiConfigWithNoEmptyFields();

        assertThrows(NullPointerException.class, () -> classUnderTest.putGraphQlApiConfig(config));
    }

    @Test
    void test_putGraphQlApiConfig_passes_exception_when_mapperService_throws_exception() throws JsonProcessingException {
        when(mapperService.extractGitHubFileRequestFrom(any(GraphQLApiConfig.class))).thenThrow(NullPointerException.class);
        GraphQLApiConfig config = getGraphQLApiConfigWithNoEmptyFields();

        assertThrows(NullPointerException.class, () -> classUnderTest.putGraphQlApiConfig(config));
    }

    @Test
    void test_putGraphQlApiConfig_passes_exception_when_callService_throws_exception() {
        when(callService.put(anyString(), any(HttpEntity.class))).thenThrow(NullPointerException.class);
        GraphQLApiConfig config = getGraphQLApiConfigWithNoEmptyFields();

        assertThrows(NullPointerException.class, () -> classUnderTest.putGraphQlApiConfig(config));
    }

    @Test
    void test_putGraphQlApiConfig_with_GraphQlApiConfig_as_null_throws_exception() {
        assertThrows(NullPointerException.class, () -> classUnderTest.putGraphQlApiConfig(null));
    }
}