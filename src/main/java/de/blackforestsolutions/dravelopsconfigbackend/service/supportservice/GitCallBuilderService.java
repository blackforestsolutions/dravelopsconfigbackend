package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;

public interface GitCallBuilderService{

    String buildGitUrlWith(ApiToken apiToken);

    String buildFileSubPathInGitWith(ApiToken apiToken);
}