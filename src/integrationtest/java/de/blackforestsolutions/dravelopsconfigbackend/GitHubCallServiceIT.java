package de.blackforestsolutions.dravelopsconfigbackend;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.blackforestsolutions.dravelopsconfigbackend.objectmothers.GitHubFileRequestObjectMother;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.GitHubHttpCallBuilderService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsHttpCallBuilder;
import de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsJsonMapper;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GitHubCallServiceIT{

    @Autowired
    private CallService classUnderTest;

    @Autowired
    private GitHubHttpCallBuilderService callBuilderService;

    @Autowired
    private ApiToken gitHubConfigApiToken;

    @Test
    void test_get_with_correct_variables_succeeds_request() throws JsonProcessingException {
        DravelOpsJsonMapper mapper = new DravelOpsJsonMapper();
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        HttpEntity<?> getHttpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> result = classUnderTest.get(testUrl, getHttpEntity);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotEmpty();
        assertThat(mapper.readValue(result.getBody(), GitHubFileResponse.class)).isInstanceOf(GitHubFileResponse.class);
        assertThat(mapper.readValue(result.getBody(), GitHubFileResponse.class).getContent()).isNotEmpty();
        assertThat(mapper.readValue(result.getBody(), GitHubFileResponse.class).getSha()).isNotEmpty();
        assertThat(mapper.readValue(result.getBody(), GitHubFileResponse.class).getPath()).isEqualTo(testApiToken.getFilepath());
    }

    @Test
    void test_get_with_incorrect_api_token_path_throws_ResourceAccessException() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        testApiToken.setPath("wrong path");
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        HttpEntity<?> getHttpEntity = new HttpEntity<>(headers);

        assertThrows(ResourceAccessException.class, () -> classUnderTest.get(testUrl, getHttpEntity));
    }

    @Test
    void test_get_with_incorrect_api_token_repository_throws_HttpClientErrorException_NotFound() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        testApiToken.setRepository("wrong/repository");
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        HttpEntity<?> getHttpEntity = new HttpEntity<>(headers);

        assertThrows(HttpClientErrorException.NotFound.class, () -> classUnderTest.get(testUrl, getHttpEntity));
    }

    @Test
    void test_get_with_incorrect_api_token_password_throws_HttpClientErrorException_NotFound() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        testApiToken.setPassword("wrongPasswordToken");
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        HttpEntity<?> getHttpEntity = new HttpEntity<>(headers);

        assertThrows(HttpClientErrorException.NotFound.class, () -> classUnderTest.get(testUrl, getHttpEntity));
    }

    @Test
    void test_get_with_incorrect_api_token_host_throws_ResourceAccessException() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        testApiToken.setHost("wrongHost");
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        HttpEntity<?> getHttpEntity = new HttpEntity<>(headers);

        assertThrows(ResourceAccessException.class, () -> classUnderTest.get(testUrl, getHttpEntity));
    }

    @Test
    void test_get_without_requestEntity_throws_HttpClientErrorException_NotFound() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();

        assertThrows(HttpClientErrorException.NotFound.class, () -> classUnderTest.get(testUrl, null));
    }

    @Test
    void test_get_without_url_throws_ResourceAccessException() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        HttpEntity<?> getHttpEntity = new HttpEntity<>(headers);

        assertThrows(ResourceAccessException.class, () -> classUnderTest.get(null, getHttpEntity));
    }

    @Test
    void test_get_without_url_and_requestEntity_throws_ResourceAccessException() {
        assertThrows(ResourceAccessException.class, () -> classUnderTest.get(null, null));
    }

    @Test
    void test_get_with_empty_HttpHeaders_throws_HttpClientErrorException_NotFound() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpEntity<?> getHttpEntity = new HttpEntity<>(null);

        assertThrows(HttpClientErrorException.NotFound.class, () -> classUnderTest.get(testUrl, getHttpEntity));
    }


    @Test
    void test_getGraphQlApiConfig_from_gitHub_with_correct_api_token_succeeds_request() throws JsonProcessingException {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        HttpEntity<?> getHttpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> getResult = classUnderTest.get(testUrl, getHttpEntity);
        DravelOpsJsonMapper mapper = new DravelOpsJsonMapper();
        String currentSha = mapper.readValue(getResult.getBody(), GitHubFileResponse.class).getSha();
        GitHubFileRequest gitHubFileRequest = GitHubFileRequestObjectMother.getCorrectGitHubFileRequest();
        gitHubFileRequest.setSha(currentSha);
        HttpEntity<?> putHttpEntity = new HttpEntity<>(gitHubFileRequest, headers);


        ResponseEntity<String> putResult = classUnderTest.put(testUrl, putHttpEntity);


        assertThat(putResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(putResult.getBody()).isNotEmpty();
    }


    @Test
    void test_getGraphQlApiConfig_from_gitHub_with_incorrect_api_token_password_throws_HttpClientErrorException$NotFound() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        testApiToken.setPassword("wrongPassword");
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        GitHubFileRequest request = GitHubFileRequestObjectMother.getCorrectGitHubFileRequest();
        HttpEntity<?> putHttpEntity = new HttpEntity<>(request, headers);

        assertThrows(HttpClientErrorException.NotFound.class, () -> classUnderTest.put(testUrl, putHttpEntity));
    }

    @Test
    void test_getGraphQlApiConfig_from_gitHub_with_incorrect_api_token_path_throws_HttpClientErrorException$Conflict() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        testApiToken.setPath("Wrong/Path");
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        GitHubFileRequest gitHubFileRequest = GitHubFileRequestObjectMother.getCorrectGitHubFileRequest();
        HttpEntity<?> putHttpEntity = new HttpEntity<>(gitHubFileRequest, headers);

        assertThrows(HttpClientErrorException.Conflict.class, () -> classUnderTest.put(testUrl, putHttpEntity));
    }

    @Test
    void test_getGraphQlApiConfig_from_gitHub_with_incorrect_api_token_sha_throws_HttpClientErrorException$Conflict() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        GitHubFileRequest gitHubFileRequest = GitHubFileRequestObjectMother.getCorrectGitHubFileRequest();
        gitHubFileRequest.setSha("wrongSha");
        HttpEntity<?> testHttpEntity = new HttpEntity<>(gitHubFileRequest, headers);

        assertThrows(HttpClientErrorException.Conflict.class, () -> classUnderTest.put(testUrl, testHttpEntity));
    }

    @Test
    void test_getGraphQlApiConfig_from_gitHub_with_missing_body_throws_HttpClientErrorException$BadRequest() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpHeaders headers = callBuilderService.buildGitHubHttpHeaderWith(testApiToken);
        HttpEntity<?> testHttpEntity = new HttpEntity<>(headers);

        assertThrows(HttpClientErrorException.BadRequest.class, () -> classUnderTest.put(testUrl, testHttpEntity));
    }

    @Test
    void test_getGraphQlApiConfig_from_gitHub_with_missing_HttpHeaders_throws_HttpClientErrorException$NotFound() {
        ApiToken testApiToken = new ApiToken(gitHubConfigApiToken);
        String testPath = callBuilderService.buildGitHubPathWith(testApiToken);
        testApiToken.setPath(testPath);
        String testUrl = DravelOpsHttpCallBuilder.buildUrlWith(testApiToken).toString();
        HttpEntity<?> testHttpEntity = new HttpEntity<>(null);

        assertThrows(HttpClientErrorException.NotFound.class, () -> classUnderTest.put(testUrl, testHttpEntity));
    }
}