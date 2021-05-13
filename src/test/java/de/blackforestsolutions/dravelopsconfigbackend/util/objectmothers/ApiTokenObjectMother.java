package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;

public class ApiTokenObjectMother{

    private static final String DEFAULT_REPOSITORY = "TestName/TestRepoName";
    private static final String PROTOCOL = "https";
    private static final String HOST = "api.github.com";
    private static final String DEFAULT_FILE_PATH = "projects/sbg/application-sbg.yaml";
    private static final String PASSWORD = "1234";

    public static ApiToken getCorrectApiToken() {
        ApiToken apiToken = new ApiToken();
        apiToken.setFilepath(DEFAULT_FILE_PATH);
        apiToken.setRepository(DEFAULT_REPOSITORY);
        apiToken.setProtocol(PROTOCOL);
        apiToken.setHost(HOST);
        apiToken.setPassword(PASSWORD);
        return apiToken;
    }
}