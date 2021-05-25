package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;

public class ApiTokenObjectMother{

    private static final String DEFAULT_REPOSITORY = "blackforestsolutions/dravelopsdeployment";
    private static final String PROTOCOL = "https";
    private static final String HOST = "api.github.com";
    private static final String DEFAULT_FILE_PATH = "projects/sbg/application-sbg.yml";
    private static final String PASSWORD = "token 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";

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