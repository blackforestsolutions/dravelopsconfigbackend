package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.ExceptionHandlerService;
import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsdatamodel.Status;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.blackforestsolutions.dravelopsdatamodel.GraphQlTab.JOURNEY_QUERY;
import static de.blackforestsolutions.dravelopsdatamodel.GraphQlTab.JOURNEY_SUBSCRIPTION;

@Service
public class GraphQlConfigIntegrationServiceImpl implements GraphQlConfigIntegrationService {

    private final GitHubApiService gitHubApiService;
    private final RefreshConfigService refreshConfigService;
    private final TestSoftwareService testSoftwareService;
    private final ExceptionHandlerService exceptionHandlerService;

    @Autowired
    public GraphQlConfigIntegrationServiceImpl(GitHubApiService gitHubApiService, RefreshConfigService refreshConfigService, TestSoftwareService testSoftwareService, ExceptionHandlerService exceptionHandlerService) {
        this.gitHubApiService = gitHubApiService;
        this.refreshConfigService = refreshConfigService;
        this.testSoftwareService = testSoftwareService;
        this.exceptionHandlerService = exceptionHandlerService;
    }

    @Override
    public ResponseEntity<GraphQLApiConfig> getGraphQlApiConfig() {
        try {
            GraphQLApiConfig graphQLApiConfig = gitHubApiService.getGraphQlApiConfig();
            return new ResponseEntity<>(graphQLApiConfig, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandlerService.handleResponseEntityException(e);
        }
    }

    @Override
    public ResponseEntity<List<CallStatus<GraphQlTab>>> putGraphQlApiConfig(GraphQLApiConfig apiConfig) {
        try {
            List<CallStatus<GraphQlTab>> callStatuses = testSoftwareService.getTestSoftwareResultWith(apiConfig);
            if (callStatuses.stream().anyMatch(tab -> tab.getStatus().equals(Status.FAILED))) {
                return new ResponseEntity<>(callStatuses, HttpStatus.OK);
            }

            gitHubApiService.putGraphQlApiConfig(apiConfig);
            refreshConfigService.refreshConfigs();
            return new ResponseEntity<>(callStatuses, HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandlerService.handleResponseEntityExceptions(e);
        }
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void updateDates() {
        try {
            ResponseEntity<GraphQLApiConfig> responseEntity = getGraphQlApiConfig();
            GraphQLApiConfig graphQLApiConfig = responseEntity.getBody();
            if (graphQLApiConfig == null) {
                return;
            }
            Map<String, Tab> map = graphQLApiConfig.getGraphql().getPlayground().getTabs();

            List<Map.Entry<String, Tab>> tabList = map.entrySet().stream()
                    .filter(this::isTabSubscriptionOrJourney)
                    .map(this::handleMapEntry)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            if (! tabList.isEmpty()) {
                tabList.forEach(entry -> map.put(entry.getKey(), entry.getValue()));
                graphQLApiConfig.getGraphql().getPlayground().setTabs(map);
                putGraphQlApiConfig(graphQLApiConfig);
            }
        } catch (Exception e) {
            exceptionHandlerService.handleExceptions(e);
        }
    }

    private boolean isTabSubscriptionOrJourney(Map.Entry<String, Tab> entry) {
        return entry.getKey().equals(JOURNEY_SUBSCRIPTION.toString()) || entry.getKey().equals(JOURNEY_QUERY.toString());
    }

    private Optional<Map.Entry<String, Tab>> handleMapEntry(Map.Entry<String, Tab> entry) {
        ZonedDateTime zoneDateTime = ZonedDateTime.parse(entry.getValue().getVariables().getDateTime());
        if (! zoneDateTime.isBefore(ZonedDateTime.now())) {
            return Optional.empty();
        }
        zoneDateTime = LocalDate.now().plusDays(1L).atTime(zoneDateTime.toLocalTime()).atZone(zoneDateTime.getZone());
        entry.getValue().getVariables().setDateTime(zoneDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return Optional.of(entry);
    }
}