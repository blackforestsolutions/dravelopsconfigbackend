package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;

public class ApiTokenObjectMother {

    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final int CONFIG_BACKEND_PORT = 8092;
    private static final int TEST_SOFTWARE_PORT = 8082;
    private static final String TEST_SOFTWARE_CALL_STATUS_PATH = "/tests/execute";
    private static final String DEFAULT_REPOSITORY = "https://github.com/TestName/TestRepoName";
    private static final String DEFAULT_FILE_PATH = "projects";
    private static final String DEFAULT_FILE_NAME = "application";
    private static final String DEFAULT_FILE_SUFFIX = ".yaml";

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
        apiToken.setPath(DEFAULT_FILE_PATH);
        apiToken.setRepository(DEFAULT_REPOSITORY);
        //apiToken.setFilename(DEFAULT_FILE_NAME);
        //apiToken.setFileSuffix(DEFAULT_FILE_SUFFIX);
        return apiToken;
    }
}
