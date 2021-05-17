package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.ExceptionHandlerService;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsconfigbackend.service.supportservice.TestSoftwareHttpCallBuilderService;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsJsonMapper;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsHttpCallBuilder.buildUrlWith;

@Service
public class TestSoftwareServiceImpl implements TestSoftwareService {

    private final CallService callService;
    private final TestSoftwareHttpCallBuilderService testSoftwareHttpCallBuilderService;
    private final ExceptionHandlerService exceptionHandlerService;
    private final ApiToken testSoftwareApiToken;

    @Autowired
    public TestSoftwareServiceImpl(CallService callService, TestSoftwareHttpCallBuilderService testSoftwareHttpCallBuilderService, ExceptionHandlerService exceptionHandlerService, ApiToken testSoftwareApiToken) {
        this.callService = callService;
        this.testSoftwareHttpCallBuilderService = testSoftwareHttpCallBuilderService;
        this.exceptionHandlerService = exceptionHandlerService;
        this.testSoftwareApiToken = testSoftwareApiToken;
    }

    @Override
    public List<CallStatus<GraphQlTab>> getTestSoftwareResultWith(GraphQLApiConfig apiConfig) {
        try {
            String url = buildRequestUrl();
            HttpEntity<Map<GraphQlTab, ApiToken>> requestEntity = testSoftwareHttpCallBuilderService.buildTestSoftwareHttpEntityWith(apiConfig);
            ResponseEntity<String> result = callService.post(url, requestEntity);
            return extractCallStatusListFrom(result.getBody());
        } catch (Exception e) {
            return exceptionHandlerService.handleExceptions(e);
        }
    }

    private String buildRequestUrl() {
        ApiToken requestToken = new ApiToken(testSoftwareApiToken);
        return buildUrlWith(requestToken).toString();
    }

    private List<CallStatus<GraphQlTab>> extractCallStatusListFrom(String jsonBody) throws JsonProcessingException {
        DravelOpsJsonMapper mapper = new DravelOpsJsonMapper();
        JavaType callStatusType = mapper.getTypeFactory().constructParametricType(CallStatus.class, GraphQlTab.class);
        JavaType callStatusListType = mapper.getTypeFactory().constructCollectionType(List.class, callStatusType);

        return mapper.readValue(jsonBody, callStatusListType);
    }

}
