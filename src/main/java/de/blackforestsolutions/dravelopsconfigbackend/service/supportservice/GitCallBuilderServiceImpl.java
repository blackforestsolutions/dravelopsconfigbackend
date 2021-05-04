package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.exception.NoResultFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
public class GitCallBuilderServiceImpl implements GitCallBuilderService{

    private final Environment environment;
    private static final String DEV_ENVIRONMENT = "dev";
    private static final String PROD_ENVIRONMENT = "prd";
    private static final String TEST_ENVIRONMENT = "test";

    @Autowired
    public GitCallBuilderServiceImpl(Environment environment) {
        this.environment = environment;
    }

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
                .concat(getCustomerProfile());
    }

    /*

                .concat(buildFileName(apiToken))
     */

    @Override
    public String buildFileName(ApiToken apiToken) {
        Objects.requireNonNull(apiToken.getFilename(), "filename is not allowed to be null");
        Objects.requireNonNull(apiToken.getFileSuffix(), "fileSuffix is not allowed to be null");

        return apiToken.getFilename()
                .concat("-")
                .concat(getCustomerProfile())
                .concat(apiToken.getFileSuffix());
    }

    private String getCustomerProfile() {
        return Arrays.stream(environment.getActiveProfiles())
                .filter(name -> ! name.equals(DEV_ENVIRONMENT) && ! name.equals(PROD_ENVIRONMENT) && ! name.equals(TEST_ENVIRONMENT))
                .findFirst()
                .orElseThrow(NoResultFoundException::new);
    }
}