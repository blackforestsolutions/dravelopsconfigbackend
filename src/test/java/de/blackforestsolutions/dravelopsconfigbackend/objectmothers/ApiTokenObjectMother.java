package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;

public class ApiTokenObjectMother {
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final int CONFIG_BACKEND_PORT = 8092;

    public static ApiToken getConfigBackendConfigurationApiToken() {
        ApiToken apiToken = new ApiToken();

        apiToken.setProtocol(PROTOCOL);
        apiToken.setHost(HOST);
        apiToken.setPort(CONFIG_BACKEND_PORT);

        return apiToken;
    }
}
