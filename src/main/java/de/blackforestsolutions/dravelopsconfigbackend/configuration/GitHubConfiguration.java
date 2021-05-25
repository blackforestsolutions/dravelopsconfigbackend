package de.blackforestsolutions.dravelopsconfigbackend.configuration;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

@RefreshScope
@SpringBootConfiguration
public class GitHubConfiguration {

    @Value("${github.protocol}")
    private String configBackendProtocol;
    @Value("${github.host}")
    private String configBackendHost;
    @Value("${github.repository}")
    private String repository;
    @Value("${github.filepath}")
    private String filepath;
    @Value("${github.password}")
    private String password;

    @RefreshScope
    @Bean
    public ApiToken gitHubConfigApiToken() {
        ApiToken apiToken = new ApiToken();
        apiToken.setProtocol(configBackendProtocol);
        apiToken.setHost(configBackendHost);
        apiToken.setRepository(repository);
        apiToken.setFilepath(filepath);
        apiToken.setPassword(password);
        return apiToken;
    }
}