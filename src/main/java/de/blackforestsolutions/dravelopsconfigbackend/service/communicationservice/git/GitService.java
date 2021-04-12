package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.git;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public interface GitService {

    File pullFileFromGitWith(ApiToken apiToken) throws IOException, GitAPIException;


    void pushFileToGitWith(File file, ApiToken apiToken) throws IOException, GitAPIException;

}
