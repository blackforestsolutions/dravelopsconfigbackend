package de.blackforestsolutions.dravelopsconfigbackend.service.mapperservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsJsonMapper;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileRequest;
import de.blackforestsolutions.dravelopsgeneratedcontent.github.GitHubFileResponse;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class GitHubMapperServiceImpl implements GitHubMapperService{

    private static final String BACKEND_UPDATE = "Backend Update: ";
    private static final int LINE_LENGTH = 60;
    private static final String LINE_SEPARATOR = "\n";

    @Override
    public GraphQLApiConfig mapToGraphQlApiConfigWith(String jsonBody) throws JsonProcessingException {
        DravelOpsJsonMapper mapper = new DravelOpsJsonMapper();
        GitHubFileResponse gitHubFileResponse = mapper.readValue(jsonBody, GitHubFileResponse.class);
        return mapGitHubFileResponseToGraphQLApiConfig(gitHubFileResponse, mapper);
    }

    @Override
    public GitHubFileRequest mapToGitHubFileRequestWith(GraphQLApiConfig graphQLApiConfig) throws JsonProcessingException {
        GitHubFileRequest gitHubFileRequest = new GitHubFileRequest();
        gitHubFileRequest.setSha(graphQLApiConfig.getSha());
        gitHubFileRequest.setMessage(BACKEND_UPDATE.concat(LocalDateTime.now().toString()));
        gitHubFileRequest.setContent(mapGraphQLApiConfigToBase64String(graphQLApiConfig) + LINE_SEPARATOR);
        return gitHubFileRequest;
    }

    private GraphQLApiConfig mapGitHubFileResponseToGraphQLApiConfig(GitHubFileResponse gitHubFileResponse, DravelOpsJsonMapper mapper) throws JsonProcessingException {
        String decodedString = new String(Base64.getMimeDecoder().decode(gitHubFileResponse.getContent()));
        String yamlString = mapper.writeValueAsString(new Yaml().load(decodedString));
        GraphQLApiConfig graphQLApiConfig = mapper.readValue(yamlString, GraphQLApiConfig.class);
        graphQLApiConfig.setSha(gitHubFileResponse.getSha());
        return graphQLApiConfig;
    }

    private String mapGraphQLApiConfigToBase64String(GraphQLApiConfig graphQLApiConfig) throws JsonProcessingException {
        DravelOpsJsonMapper mapper = new DravelOpsJsonMapper();
        JsonNode jsonNode = mapper.readTree(mapper.writeValueAsString(graphQLApiConfig));
        String yamlString = new YAMLMapper().writeValueAsString(jsonNode);
        return Base64.getMimeEncoder(LINE_LENGTH, LINE_SEPARATOR.getBytes(StandardCharsets.UTF_8))
                .encodeToString(yamlString.getBytes(StandardCharsets.UTF_8));
    }
}