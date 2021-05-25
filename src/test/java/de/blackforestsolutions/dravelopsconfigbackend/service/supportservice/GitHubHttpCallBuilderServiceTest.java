package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;


import de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.ApiTokenObjectMother;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GitHubHttpCallBuilderServiceTest{

    private final GitHubHttpCallBuilderService classUnderTest = new GitHubHttpCallBuilderServiceImpl();

    @Test
    void test_buildGitHubPathWith_correct_api_token_returns_valid_path() {
        ApiToken apiToken = ApiTokenObjectMother.getCorrectApiToken();

        assertThat(classUnderTest.buildGitHubPathWith(apiToken))
                .isEqualTo("/repos/blackforestsolutions/dravelopsdeployment/contents/projects/sbg/application-sbg.yml");
    }

    @Test
    void test_buildGitHubPathWith_api_token_which_misses_repository_throws_exception() {
        ApiToken apiToken = ApiTokenObjectMother.getCorrectApiToken();
        apiToken.setRepository(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildGitHubPathWith(apiToken));
    }

    @Test
    void test_buildGitHubPathWith_api_token_which_misses_filepath_throws_exception() {
        ApiToken apiToken = ApiTokenObjectMother.getCorrectApiToken();
        apiToken.setFilepath(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildGitHubPathWith(apiToken));
    }

    @Test
    void test_buildGitHubHttpHeaderWith_correct_apiToken_returns_correct_http_header() {
        ApiToken apiToken = ApiTokenObjectMother.getCorrectApiToken();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, apiToken.getPassword());

        assertThat(httpHeaders).isEqualTo(classUnderTest.buildGitHubHttpHeaderWith(apiToken));
    }

    @Test
    void test_buildGitHubHttpHeaderWith_apiToken_which_misses_password_throws_exception() {
        ApiToken apiToken = ApiTokenObjectMother.getCorrectApiToken();
        apiToken.setPassword(null);

        assertThrows(NullPointerException.class, () -> classUnderTest.buildGitHubHttpHeaderWith(apiToken));
    }

}