package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GitHubHttpCallBuilderServiceImpl implements GitHubHttpCallBuilderService{

    private static final String REPO_PATH = "repos";
    private static final String CONTENTS_PATH = "contents";

    @Override
    public String buildGitHubPathWith(@NonNull ApiToken apiToken) {
        Objects.requireNonNull(apiToken.getRepository(), "repository is not allowed to be null");
        Objects.requireNonNull(apiToken.getFilepath(), "filepath is not allowed to be null");

        return "/"
                .concat(REPO_PATH)
                .concat("/")
                .concat(apiToken.getRepository())
                .concat("/")
                .concat(CONTENTS_PATH)
                .concat("/")
                .concat(apiToken.getFilepath());
    }

    @Override
    public HttpHeaders buildGitHubHttpHeaderWith(@NonNull ApiToken apiToken) {
        Objects.requireNonNull(apiToken.getPassword(), "password is not allowed to be null");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, apiToken.getPassword());
        return httpHeaders;
    }
}