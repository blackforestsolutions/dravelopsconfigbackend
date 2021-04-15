package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.ApiTokenObjectMother.getCorrectApiToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GitCallBuilderServiceTest{

    private final MockEnvironment environment = new MockEnvironment();

    private final GitCallBuilderService classUnderTest = new GitCallBuilderServiceImpl(environment);

    @BeforeEach
    void init() {
        environment.setProperty("spring.profiles.active", "dev,sbg");
    }

    @Test
    void test_buildFileNameWith_apiToken_and_dev_environment_returns_valid_name() {
        ApiToken testData = getCorrectApiToken();

        String result = classUnderTest.buildFileName(testData);

        assertThat(result).isEqualTo("application-sbg.yaml");
    }

    @Test
    void test_buildFileNameWith_apiToken_and_prd_environment_returns_valid_name() {
        ApiToken testData = getCorrectApiToken();
        environment.setProperty("spring.profiles.active", "prd,sbg");

        String result = classUnderTest.buildFileName(testData);

        assertThat(result).isEqualTo("application-sbg.yaml");
    }

    @Test
    void test_buildFileSubPathWith_apiToken_and_dev_environment_returns_valid_path() {
        ApiToken testData = getCorrectApiToken();

        String result = classUnderTest.buildFileSubPathInGitWith(testData);

        assertThat(result).isEqualTo("projects/sbg/application-sbg.yaml");
    }

    @Test
    void test_buildFileSubPathWith_apiToken_and_prd_environment_returns_valid_path() {
        ApiToken testData = getCorrectApiToken();
        environment.setProperty("spring.profiles.active", "prd,sbg");

        String result = classUnderTest.buildFileSubPathInGitWith(testData);

        assertThat(result).isEqualTo("projects/sbg/application-sbg.yaml");
    }

    @Test
    void test_buildRepoUrlWith_apiToken_and_dev_environment_returns_valid_url() {
        ApiToken testData = getCorrectApiToken();

        String result = classUnderTest.buildGitUrlWith(testData);

        assertThat(result).isEqualTo("https://github.com/TestName/TestRepoName/projects/sbg/application-sbg.yaml");
    }

    @Test
    void test_buildRepoUrlWith_apiToken_and_prd_environment_returns_valid_url() {
        ApiToken testData = getCorrectApiToken();
        environment.setProperty("spring.profiles.active", "prd,sbg");

        String result = classUnderTest.buildGitUrlWith(testData);

        assertThat(result).isEqualTo("https://github.com/TestName/TestRepoName/projects/sbg/application-sbg.yaml");
    }


    @Test
    void test_buildFileNameWith_apiToken_and_dev_environment_and_fileName_as_null_throws_exception() {
        ApiToken testData = getCorrectApiToken();
        testData.setFilename(null);
        assertThrows(NullPointerException.class, () -> classUnderTest.buildFileName(testData));
    }

    @Test
    void test_buildFileNameWith_apiToken_and_dev_environment_and_fileSuffix_as_null_throws_exception() {
        ApiToken testData = getCorrectApiToken();
        testData.setFileSuffix(null);
        assertThrows(NullPointerException.class, () -> classUnderTest.buildFileName(testData));
    }

    @Test
    void test_buildFileSubPathWith_apiToken_and_dev_environment_and_fileName_as_null_throws_exception() {
        ApiToken testData = getCorrectApiToken();
        testData.setFilename(null);
        assertThrows(NullPointerException.class, () -> classUnderTest.buildFileSubPathInGitWith(testData));
    }

    @Test
    void test_buildFileSubPathWith_apiToken_and_dev_environment_and_fileSuffix_as_null_throws_exception() {
        ApiToken testData = getCorrectApiToken();
        testData.setFileSuffix(null);
        assertThrows(NullPointerException.class, () -> classUnderTest.buildFileSubPathInGitWith(testData));
    }

    @Test
    void test_buildFileSubPathWith_apiToken_and_dev_environment_and_path_as_null_throws_exception() {
        ApiToken testData = getCorrectApiToken();
        testData.setPath(null);
        assertThrows(NullPointerException.class, () -> classUnderTest.buildFileSubPathInGitWith(testData));
    }


    @Test
    void test_buildRepoUrlWith_apiToken_and_dev_environment_and_fileName_as_null_throws_exception() {
        ApiToken testData = getCorrectApiToken();
        testData.setFilename(null);
        assertThrows(NullPointerException.class, () -> classUnderTest.buildGitUrlWith(testData));
    }

    @Test
    void test_buildRepoUrlWith_apiToken_and_dev_environment_and_fileSuffix_as_null_throws_exception() {
        ApiToken testData = getCorrectApiToken();
        testData.setFileSuffix(null);
        assertThrows(NullPointerException.class, () -> classUnderTest.buildGitUrlWith(testData));
    }

    @Test
    void test_buildRepoUrlWith_apiToken_and_dev_environment_and_path_as_null_throws_exception() {
        ApiToken testData = getCorrectApiToken();
        testData.setPath(null);
        assertThrows(NullPointerException.class, () -> classUnderTest.buildGitUrlWith(testData));
    }

    @Test
    void test_buildRepoUrlWith_apiToken_and_dev_environment_and_repository_as_null_throws_exception() {
        ApiToken testData = getCorrectApiToken();
        testData.setRepository(null);
        assertThrows(NullPointerException.class, () -> classUnderTest.buildGitUrlWith(testData));
    }
}