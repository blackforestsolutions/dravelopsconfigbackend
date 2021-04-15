package de.blackforestsolutions.dravelopsconfigbackend.controller;

import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.git.GitService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("configbackend")
public class GraphQlConfigController{

    private final GitService gitService;

    @Autowired
    public GraphQlConfigController(GitService gitService) {
        this.gitService = gitService;
    }

    @GetMapping
    public File pullFileFromGitWith(ApiToken apiToken) throws GitAPIException, IOException {
        return gitService.pullFileFromGitWith(apiToken);
    }

    @PostMapping
    public List<RemoteRefUpdate.Status> pushFileToGitWith(File jsonFile, ApiToken apiToken) throws GitAPIException, IOException {
        return gitService.pushFileToGitWith(jsonFile, apiToken);
    }
}