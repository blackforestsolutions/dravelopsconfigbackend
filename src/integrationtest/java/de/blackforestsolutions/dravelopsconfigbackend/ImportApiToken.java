package de.blackforestsolutions.dravelopsconfigbackend;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ImportApiToken{

    private static final String REPOSITORY = "Luca1235/TestDeployment";
    private static final String FILE_PATH = "projects/sbg/application-sbg.yaml";
    private static final String PROTOCOL = "https";
    private static final String HOST = "api.github.com";
    private static final String PASSWORD = "f186e448c9c80c242f4c7ddd8e10182196c8a261";
    private static final String USERNAME = "Luca1235";

    @Bean
    @ConfigurationProperties(prefix = "correct")
    public ApiToken correctApiToken() {
        ApiToken apiToken = new ApiToken();
        apiToken.setPassword(PASSWORD);
        apiToken.setRepository(REPOSITORY);
        apiToken.setFilepath(FILE_PATH);
        apiToken.setHost(HOST);
        apiToken.setProtocol(PROTOCOL);
        return apiToken;
    }
}
