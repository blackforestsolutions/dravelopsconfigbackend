package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
public class GitCallBuilderServiceImpl implements GitCallBuilderService{

    @Override
    public String buildGitUrlWith(ApiToken apiToken) {
        Objects.requireNonNull(apiToken.getRepository(), "repository is not allowed to be null");

        return apiToken.getRepository()
                .concat("/")
                .concat(buildFileSubPathInGitWith(apiToken));
    }

    @Override
    public String buildFileSubPathInGitWith(ApiToken apiToken) {
        Objects.requireNonNull(apiToken.getPath(), "path is not allowed to be null");

        return apiToken.getPath()
                .concat("/")
                .concat(getCustomerProfile())
                .concat("/")
                .concat(buildFileName(apiToken));
    }
}