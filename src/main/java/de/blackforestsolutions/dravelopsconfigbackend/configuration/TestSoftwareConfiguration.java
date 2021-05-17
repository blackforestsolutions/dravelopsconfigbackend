package de.blackforestsolutions.dravelopsconfigbackend.configuration;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

@RefreshScope
@SpringBootConfiguration
public class TestSoftwareConfiguration {

    @Value("${testsoftware.protocol}")
    private String testSoftwareProtocol;
    @Value("${testsoftware.host}")
    private String testSoftwareHost;
    @Value("${testsoftware.port}")
    private int testSoftwarePort;
    @Value("${testsoftware.get.test.path}")
    private String testSoftwarePath;

    @RefreshScope
    @Bean
    public ApiToken testSoftwareApiToken() {
        ApiToken apiToken = new ApiToken();

        apiToken.setProtocol(testSoftwareProtocol);
        apiToken.setHost(testSoftwareHost);
        apiToken.setPort(testSoftwarePort);
        apiToken.setPath(testSoftwarePath);

        return apiToken;
    }
}
