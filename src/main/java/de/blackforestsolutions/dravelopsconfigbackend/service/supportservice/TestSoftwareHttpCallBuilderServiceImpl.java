package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsdatamodel.Point;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.GraphQLApiConfig;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Variables;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Service
public class TestSoftwareHttpCallBuilderServiceImpl implements TestSoftwareHttpCallBuilderService {

    @Override
    public HttpEntity<Map<GraphQlTab, ApiToken>> buildTestSoftwareHttpEntityWith(GraphQLApiConfig apiConfig) {
        Map<GraphQlTab, ApiToken> body = buildGraphQlRequestTabsWith(apiConfig);
        return new HttpEntity<>(body);
    }

    private Map<GraphQlTab, ApiToken> buildGraphQlRequestTabsWith(GraphQLApiConfig apiConfig) {
        Objects.requireNonNull(apiConfig.getGraphql().getPlayground().getTabs(), "tabs is not allowed to be null");
        Objects.requireNonNull(apiConfig.getGraphql().getPlayground().getTabs().get(GraphQlTab.JOURNEY_QUERY.toString()), "journey query is not allowed to be null");
        Objects.requireNonNull(apiConfig.getGraphql().getPlayground().getTabs().get(GraphQlTab.ADDRESS_AUTOCOMPLETION.toString()), "adress autocompletion is not allowed to be null");
        Objects.requireNonNull(apiConfig.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_ADDRESSES.toString()), "nearest adresses is not allowed to be null");
        Objects.requireNonNull(apiConfig.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_STATIONS.toString()), "nearest stations is not allowed to be null");

        Map<GraphQlTab, ApiToken> testData = new HashMap<>();

        testData.put(GraphQlTab.JOURNEY_QUERY, buildJourneyApiTokenWith(apiConfig.getGraphql().getPlayground().getTabs().get(GraphQlTab.JOURNEY_QUERY.toString()).getVariables()));
        testData.put(GraphQlTab.ADDRESS_AUTOCOMPLETION, buildAddressAutocompletionApiTokenWith(apiConfig.getGraphql().getPlayground().getTabs().get(GraphQlTab.ADDRESS_AUTOCOMPLETION.toString()).getVariables()));
        testData.put(GraphQlTab.NEAREST_ADDRESSES, buildNearestApiTokenWith(apiConfig.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_ADDRESSES.toString()).getVariables()));
        testData.put(GraphQlTab.NEAREST_STATIONS, buildNearestApiTokenWith(apiConfig.getGraphql().getPlayground().getTabs().get(GraphQlTab.NEAREST_STATIONS.toString()).getVariables()));

        return testData;
    }

    private ApiToken buildJourneyApiTokenWith(Variables variables) {
        Objects.requireNonNull(variables.getDepartureLongitude(), "departureLongitude is not allowed to be null");
        Objects.requireNonNull(variables.getArrivalLongitude(), "arrivalLongitude is not allowed to be null");
        Objects.requireNonNull(variables.getDateTime(), "dateTime is not allowed to be null");
        Objects.requireNonNull(variables.getIsArrivalDateTime(), "arrivalDateTime is not allowed to be null");
        Objects.requireNonNull(variables.getLanguage(), "language is not allowed to be null");

        ApiToken apiToken = new ApiToken();

        apiToken.setDepartureCoordinate(new Point.PointBuilder(variables.getDepartureLongitude(), variables.getDepartureLatitude()).build());
        apiToken.setArrivalCoordinate(new Point.PointBuilder(variables.getArrivalLongitude(), variables.getArrivalLatitude()).build());
        apiToken.setDateTime(ZonedDateTime.parse(variables.getDateTime()));
        apiToken.setIsArrivalDateTime(variables.getIsArrivalDateTime());
        apiToken.setLanguage(new Locale(variables.getLanguage()));

        return apiToken;
    }

    private ApiToken buildAddressAutocompletionApiTokenWith(Variables variables) {
        Objects.requireNonNull(variables.getText(), "text is not allowed to be null");
        Objects.requireNonNull(variables.getLanguage(), "language is not allowed to be null");

        ApiToken apiToken = new ApiToken();

        apiToken.setDeparture(variables.getText());
        apiToken.setLanguage(new Locale(variables.getLanguage()));

        return apiToken;
    }

    private ApiToken buildNearestApiTokenWith(Variables variables) {
        Objects.requireNonNull(variables.getLongitude(), "arrivalLongitude is not allowed to be null");
        Objects.requireNonNull(variables.getLatitude(), "arrivalLatitude is not allowed to be null");
        Objects.requireNonNull(variables.getRadiusInKilometers(), "radius is not allowed to be null");
        Objects.requireNonNull(variables.getLanguage(), "language is not allowed to be null");

        ApiToken apiToken = new ApiToken();

        apiToken.setArrivalCoordinate(new Point.PointBuilder(variables.getLongitude(), variables.getLatitude()).build());
        apiToken.setRadiusInKilometers(new Distance(variables.getRadiusInKilometers(), Metrics.KILOMETERS));
        apiToken.setLanguage(new Locale(variables.getLanguage()));

        return apiToken;
    }

}
