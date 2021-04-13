package de.blackforestsolutions.dravelopsconfigbackend;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@TestConfiguration
public class ImportApiToken {

    private final String REPOSITORY_NAME = "TestDeployment";
    private final String REPOSITORY_LINK = "https://github.com/Luca1235/" + REPOSITORY_NAME;
    private final String FILE_PATH = "projects/sbg";
    private final String FILE_NAME = "application-sbg.yaml";

    private final String USERNAME = "Luca1235";
    private final String PASSWORD_TOKEN = "f186e448c9c80c242f4c7ddd8e10182196c8a261";
    private final String INCORRECT_PASSWORD_TOKEN = "4815162342";

    @Bean
    @ConfigurationProperties(prefix = "correct")
    public ApiToken correctApiToken() {
        ApiToken apiToken = new ApiToken();

        apiToken.setPath(FILE_PATH);
        apiToken.setUsername(USERNAME);
        apiToken.setPassword(PASSWORD_TOKEN);
        apiToken.setRepository(REPOSITORY_LINK);
        apiToken.setFilename(FILE_NAME);

        return apiToken;
    }

    @Bean
    @ConfigurationProperties(prefix = "incorrect")
    public ApiToken incorrectApiToken() {
        ApiToken apiToken = new ApiToken();

        apiToken.setPath(FILE_PATH);
        apiToken.setUsername(USERNAME);
        apiToken.setPassword(INCORRECT_PASSWORD_TOKEN);
        apiToken.setRepository(REPOSITORY_LINK);
        apiToken.setFilename(FILE_NAME);

        return apiToken;
    }
}
