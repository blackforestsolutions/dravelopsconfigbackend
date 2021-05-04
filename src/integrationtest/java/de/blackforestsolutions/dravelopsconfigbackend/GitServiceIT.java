package de.blackforestsolutions.dravelopsconfigbackend;

import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.git.GitService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.GitCallBuilderService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.testutil.TestUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(ImportApiToken.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GitServiceIT{

    @Autowired
    private GitService gitService;
    @Autowired
    private GitCallBuilderService gitCallBuilderService;

    @Autowired
    private ApiToken correctApiToken;

    private ApiToken incorrectApiToken;

    @BeforeEach
    void init() {
        correctApiToken.setPath(gitCallBuilderService.buildFileSubPathInGitWith(correctApiToken));
        correctApiToken.setFilename(gitCallBuilderService.buildFileName(correctApiToken));

        incorrectApiToken = new ApiToken(correctApiToken);
        incorrectApiToken.setPassword("wrongPassword");
    }

    @Test
    @Order(1)
    void test_pullFileFromGitWith_with_correct_api_token_is_equal_to_local_file() throws GitAPIException, IOException {
        File localFile = TestUtils.getResourceAsFile("test.files/application-sbg-test.yaml", "");

        File pulledFile = gitService.pullFileFromGitWith(correctApiToken);

        assertThat(Files.readString(Path.of(localFile.getPath())))
                .isEqualToIgnoringWhitespace(Files.readString(Path.of(pulledFile.getPath())));
    }

    @Test
    @Order(5)
    void test_pullFileFromGitWith_with_correct_api_token_is_not_equal_to_wrong_local_file() throws GitAPIException, IOException {
        File jsonFile = TestUtils.getResourceAsFile("test.files/application-sbg-test-wrong.yaml", "");

        File fetchedFile = gitService.pullFileFromGitWith(correctApiToken);

        assertThat(Files.readString(Path.of(jsonFile.getPath())))
                .isNotEqualToIgnoringWhitespace(Files.readString(Path.of(fetchedFile.getPath())));
    }

    @Test
    @Order(2)
    void test_pullFileFromGitWith_with_incorrect_api_token_throws_transportException() {
        assertThrows(TransportException.class, () -> gitService.pullFileFromGitWith(incorrectApiToken));
    }

    @Test
    @Order(3)
    void test_pushFileToGitWith_correct_api_token_returns_ok_as_status() throws GitAPIException, IOException {
        File yamlFile = TestUtils.getResourceAsFile("test.files/application-sbg-test.yaml", "");

        assertTrue(gitService.pushFileToGitWith(yamlFile, correctApiToken).stream().allMatch(status -> status.equals(RemoteRefUpdate.Status.OK)));
    }

    @Test
    @Order(4)
    void test_pushFileToGitWith_incorrect_api_token_throws_transportException() {
        File yamlFile = TestUtils.getResourceAsFile("test.files/application-sbg-test-wrong.yaml", "");
        assertThrows(TransportException.class, () -> gitService.pushFileToGitWith(yamlFile, incorrectApiToken));
    }
}