package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.git;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public interface GitService{

    /**
     * This method pulls the latest yaml file from the given repository. The filename can be obtained from ApiToken.
     *
     * @param apiToken The API Token with information of the repository, username and password
     * @return A Json File
     */
    File pullFileFromGitWith(ApiToken apiToken) throws IOException, GitAPIException;

    boolean pushFileToGitWith(File file, ApiToken apiToken) throws IOException, GitAPIException;

}
