package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.RefreshExecutionException;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsHttpCallBuilder.buildUrlWith;

@Service
public class RefreshConfigServiceImpl implements RefreshConfigService {

    public static final String ACTUATOR_REFRESH_PATH = "/actuator/bus-refresh";

    private final CallService callService;
    private final ApiToken configBackendConfigurationApiToken;

    @Autowired
    public RefreshConfigServiceImpl(CallService callService, ApiToken configBackendConfigurationApiToken) {
        this.callService = callService;
        this.configBackendConfigurationApiToken = configBackendConfigurationApiToken;
    }

    @Override
    public void refreshConfigs() {
        String requestUrl = buildRequestUrl();
        ResponseEntity<String> result = callService.post(requestUrl, HttpEntity.EMPTY);

        if (!result.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
            throw new RefreshExecutionException();
        }
    }

    private String buildRequestUrl() {
        ApiToken requestToken = new ApiToken(configBackendConfigurationApiToken);
        requestToken.setPath(ACTUATOR_REFRESH_PATH);

        return buildUrlWith(requestToken).toString();
    }

}
