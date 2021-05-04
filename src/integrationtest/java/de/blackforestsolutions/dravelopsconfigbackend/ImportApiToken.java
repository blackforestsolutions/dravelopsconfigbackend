package de.blackforestsolutions.dravelopsconfigbackend;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ImportApiToken{

    private static final String REPOSITORY_NAME = "TestDeployment";
    private static final String REPOSITORY_LINK = "https://github.com/Luca1235/" + REPOSITORY_NAME;
    private static final String FILE_PATH = "projects";
    private static final String FILE_NAME = "application";
    private static final String FILE_SUFFIX = ".yaml";

    private static final String USERNAME = "Luca1235";
    private static final String PASSWORD_TOKEN = "f186e448c9c80c242f4c7ddd8e10182196c8a261";

    @Bean
    @ConfigurationProperties(prefix = "correct")
    public ApiToken correctApiToken() {
        ApiToken apiToken = new ApiToken();

        apiToken.setPath(FILE_PATH);
        apiToken.setUsername(USERNAME);
        apiToken.setPassword(PASSWORD_TOKEN);
        apiToken.setRepository(REPOSITORY_LINK);
        apiToken.setFilename(FILE_NAME);
        apiToken.setFileSuffix(FILE_SUFFIX);

        return apiToken;
    }
}
