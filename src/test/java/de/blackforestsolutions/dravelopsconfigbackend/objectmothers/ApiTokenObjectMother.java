package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;

public class ApiTokenObjectMother {

    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final int CONFIG_BACKEND_PORT = 8092;
    private static final int TEST_SOFTWARE_PORT = 8082;
    private static final String TEST_SOFTWARE_CALL_STATUS_PATH = "/tests/execute";
    private static final String GITHUB_PROTOCOL = "https";
    private static final String GITHUB_HOST = "api.github.com";
    private static final String DEFAULT_REPOSITORY = "blackforestsolutions/dravelopsdeployment";
    private static final String DEFAULT_FILE_PATH = "projects/sbg/application-sbg.yml";
    private static final String PASSWORD = "token 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";


    public static ApiToken getConfiguredConfigBackendApiToken() {
        ApiToken apiToken = new ApiToken();

        apiToken.setProtocol(PROTOCOL);
        apiToken.setHost(HOST);
        apiToken.setPort(CONFIG_BACKEND_PORT);

        return apiToken;
    }

    public static ApiToken getConfiguredTestSoftwareApiToken() {
        ApiToken apiToken = new ApiToken();

        apiToken.setProtocol(PROTOCOL);
        apiToken.setHost(HOST);
        apiToken.setPort(TEST_SOFTWARE_PORT);
        apiToken.setPath(TEST_SOFTWARE_CALL_STATUS_PATH);

        return apiToken;
    }

    public static ApiToken getCorrectApiToken() {
        ApiToken apiToken = new ApiToken();
        apiToken.setFilepath(DEFAULT_FILE_PATH);
        apiToken.setRepository(DEFAULT_REPOSITORY);
        apiToken.setProtocol(GITHUB_PROTOCOL);
        apiToken.setHost(GITHUB_HOST);
        apiToken.setPassword(PASSWORD);
        return apiToken;
    }
}
