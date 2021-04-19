package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;

public class ApiTokenObjectMother {

    public static ApiToken getConfigBackendConfigurationApiToken() {
        ApiToken apiToken = new ApiToken();

        apiToken.setProtocol("http");
        apiToken.setHost("localhost");
        apiToken.setPort(8092);

        return apiToken;
    }
}
