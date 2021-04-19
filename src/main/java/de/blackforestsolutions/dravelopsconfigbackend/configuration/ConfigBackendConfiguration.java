package de.blackforestsolutions.dravelopsconfigbackend.configuration;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

@RefreshScope
@SpringBootConfiguration
public class ConfigBackendConfiguration {

    @Value("${configbackend.protocol}")
    private String configBackendProtocol;
    @Value("${configbackend.host}")
    private String configBackendHost;
    @Value("${configbackend.port}")
    private int configBackendPort;


    @RefreshScope
    @Bean
    public ApiToken configBackendConfigurationApiToken() {
        ApiToken apiToken = new ApiToken();

        apiToken.setProtocol(configBackendProtocol);
        apiToken.setHost(configBackendHost);
        apiToken.setPort(configBackendPort);

        return apiToken;
    }

}
