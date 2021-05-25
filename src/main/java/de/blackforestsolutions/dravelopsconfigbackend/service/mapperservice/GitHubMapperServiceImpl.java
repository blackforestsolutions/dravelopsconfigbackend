package de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.blackforestsolutions.dravelopsconfigbackend.jackson.LayersMixIn;
import de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsJsonMapper;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileResponse;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Tab;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class GitHubMapperServiceImpl implements GitHubMapperService {

    private static final String BACKEND_UPDATE = "Backend Update: ";
    private static final int LINE_LENGTH = 60;
    private static final String LINE_SEPARATOR = "\n";

    @Override
    public GraphQLApiConfig extractGraphQlApiConfigFrom(String jsonBody) throws IOException {
        DravelOpsJsonMapper mapper = new DravelOpsJsonMapper();
        GitHubFileResponse gitHubFileResponse = mapper.readValue(jsonBody, GitHubFileResponse.class);

        return extractGitHubFileRequestFrom(gitHubFileResponse);
    }

    @Override
    public GitHubFileRequest extractGitHubFileRequestFrom(GraphQLApiConfig graphQLApiConfig) throws JsonProcessingException {
        GitHubFileRequest gitHubFileRequest = new GitHubFileRequest();

        gitHubFileRequest.setSha(graphQLApiConfig.getSha());
        gitHubFileRequest.setMessage(BACKEND_UPDATE.concat(LocalDateTime.now().toString()));
        gitHubFileRequest.setContent(extractBase64From(graphQLApiConfig) + LINE_SEPARATOR);

        return gitHubFileRequest;
    }

    private GraphQLApiConfig extractGitHubFileRequestFrom(GitHubFileResponse gitHubFileResponse) throws IOException {
        byte[] yamlBytes = Base64.getMimeDecoder().decode(gitHubFileResponse.getContent());
        return extractGraphQLApiConfigFrom(yamlBytes, gitHubFileResponse.getSha());
    }

    private GraphQLApiConfig extractGraphQLApiConfigFrom(byte[] yamlBytes, String shaId) throws IOException {
        DravelOpsJsonMapper mapper = new DravelOpsJsonMapper(new YAMLFactory());
        mapper.addMixIn(Tab.class, LayersMixIn.class);

        GraphQLApiConfig graphQLApiConfig = mapper.readValue(yamlBytes, GraphQLApiConfig.class);
        graphQLApiConfig.setSha(shaId);
        return graphQLApiConfig;
    }

    private String extractBase64From(GraphQLApiConfig graphQLApiConfig) throws JsonProcessingException {
        DravelOpsJsonMapper mapper = new DravelOpsJsonMapper(new YAMLFactory());
        mapper.addMixIn(Tab.class, LayersMixIn.class);

        String yamlString = mapper.writeValueAsString(graphQLApiConfig);
        return Base64.getMimeEncoder(LINE_LENGTH, LINE_SEPARATOR.getBytes(StandardCharsets.UTF_8))
                .encodeToString(yamlString.getBytes(StandardCharsets.UTF_8));
    }
}