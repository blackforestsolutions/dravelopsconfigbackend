package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.springframework.http.HttpHeaders;

public interface GitHubHttpCallBuilderService{

    String buildGitHubPathWith(ApiToken apiToken);

    HttpHeaders buildGitHubHttpHeader(ApiToken apiToken);
}