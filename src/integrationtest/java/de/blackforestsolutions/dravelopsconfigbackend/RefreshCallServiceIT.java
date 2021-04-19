package de.blackforestsolutions.dravelopsconfigbackend;

import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.RefreshConfigServiceImpl.ACTUATOR_REFRESH_PATH;
import static de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsHttpCallBuilder.buildUrlWith;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RefreshCallServiceIT {

    @Autowired
    private CallService classUnderTest;
    @Autowired
    private ApiToken configBackendConfigurationApiToken;

    @Test
    void test_post_refresh_mechanism_returns_correct_http_status() {
        ApiToken testData = new ApiToken(configBackendConfigurationApiToken);
        testData.setPath(ACTUATOR_REFRESH_PATH);
        String testUrl = buildUrlWith(testData).toString();

        ResponseEntity<String> result = classUnderTest.post(testUrl, HttpEntity.EMPTY);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
