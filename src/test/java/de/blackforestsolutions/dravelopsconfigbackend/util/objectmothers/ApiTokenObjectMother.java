package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;

public class ApiTokenObjectMother{

    private static final String DEFAULT_REPOSITORY = "https://github.com/TestName/TestRepoName";
    private static final String DEFAULT_FILE_PATH = "projects";
    private static final String DEFAULT_FILE_NAME = "application";
    private static final String DEFAULT_FILE_SUFFIX = ".yaml";

    public static ApiToken getCorrectApiToken() {
        ApiToken apiToken = new ApiToken();
        apiToken.setPath(DEFAULT_FILE_PATH);
        apiToken.setRepository(DEFAULT_REPOSITORY);
        apiToken.setFilename(DEFAULT_FILE_NAME);
        apiToken.setFileSuffix(DEFAULT_FILE_SUFFIX);
        return apiToken;
    }

}