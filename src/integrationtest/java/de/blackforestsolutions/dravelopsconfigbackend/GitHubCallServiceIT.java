package de.blackforestsolutions.dravelopsconfigbackend;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(ImportApiToken.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GitHubCallServiceIT{

    /*
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

     */
}