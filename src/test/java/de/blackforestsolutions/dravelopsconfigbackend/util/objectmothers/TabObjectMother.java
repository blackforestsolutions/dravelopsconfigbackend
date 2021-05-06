package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Tab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Variables;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.VariablesObjectMother.*;

/**
 * Possible Fields:
 * "name",
 * "query",
 * "variables",
 * "maxResults",
 * "departurePlaceholder",
 * "arrivalPlaceholder",
 * "maxPastDaysInCalendar",
 * "layers",
 * "bufferInMetres"
 */
public class TabObjectMother{

    private static final String DEFAULT_ARRIVAL_PLACEHOLDER = "Ziel";
    private static final String DEFAULT_DEPARTURE_PLACEHOLDER = "Start";
    private static final List<String> DEFAULT_LAYERS = List.of("venue", "address", "street", "locality");

    public static Map<String, Tab> getTabsWithNoEmptyField() {
        Map<String, Tab> tabMap = new LinkedHashMap<>();
        tabMap.put(GraphQlTab.JOURNEY_QUERY.toString(), getTabJourneyQuery(getVariablesWithAllInformation()));
        tabMap.put(GraphQlTab.JOURNEY_SUBSCRIPTION.toString(), getTabJourneySubscription(getVariablesWithoutArrivalLongitude()));
        tabMap.put(GraphQlTab.ADDRESS_AUTOCOMPLETION.toString(), getTabAutocompleteAddressesQuery(getVariablesWithLanguageAndText()));
        tabMap.put(GraphQlTab.NEAREST_ADDRESSES.toString(), getTabNearestAddressesQuery(getVariablesWithLanguageLongitudeLatitudeRadius()));
        tabMap.put(GraphQlTab.NEAREST_STATIONS.toString(), getTabNearestStationsQuery(getVariablesWithLanguageLongitudeLatitudeRadius()));
        tabMap.put(GraphQlTab.ALL_STATIONS.toString(), getTabGetAllStationsQueryWithoutVariables());
        tabMap.put(GraphQlTab.OPERATING_AREA.toString(), getTabGetOperatingAreaQuery());
        return tabMap;
    }

    public static Tab getTabJourneyQuery(Variables variables) {
        Tab tab = new Tab();
        tab.setName("Journey query");
        tab.setQuery("classpath:playground/requests/get-journeys-query-max-parameters.graphql");
        tab.setMaxResults(5L);
        tab.setDeparturePlaceholder(DEFAULT_DEPARTURE_PLACEHOLDER);
        tab.setArrivalPlaceholder(DEFAULT_ARRIVAL_PLACEHOLDER);
        tab.setMaxPastDaysInCalendar(0L);
        tab.setVariables(variables);
        return tab;
    }

    public static Tab getTabJourneySubscription(Variables variables) {
        Tab tab = new Tab();
        tab.setName("Journey subscription");
        tab.setQuery("classpath:playground/requests/get-journeys-subscription-max-parameters.graphql");
        tab.setMaxResults(5L);
        tab.setDeparturePlaceholder(DEFAULT_DEPARTURE_PLACEHOLDER);
        tab.setArrivalPlaceholder(DEFAULT_ARRIVAL_PLACEHOLDER);
        tab.setMaxPastDaysInCalendar(0L);
        tab.setVariables(variables);
        return tab;
    }

    public static Tab getTabAutocompleteAddressesQuery(Variables variables) {
        Tab tab = new Tab();
        tab.setName("Autocomplete addresses query");
        tab.setQuery("classpath:playground/requests/get-autocomplete-addresses-query-max-parameters.graphql");
        tab.setMaxResults(10L);
        tab.setLayers(DEFAULT_LAYERS);
        tab.setVariables(variables);
        return tab;
    }

    public static Tab getTabNearestAddressesQuery(Variables variables) {
        Tab tab = new Tab();
        tab.setName("Nearest addresses query");
        tab.setQuery("classpath:playground/requests/get-nearest-addresses-query-max-parameters.graphql");
        tab.setMaxResults(10L);
        tab.setLayers(DEFAULT_LAYERS);
        tab.setVariables(variables);
        return tab;
    }

    public static Tab getTabNearestStationsQuery(Variables variables) {
        Tab tab = new Tab();
        tab.setName("Nearest stations query");
        tab.setQuery("classpath:playground/requests/get-nearest-stations-query-max-parameters.graphql");
        tab.setMaxResults(10L);
        tab.setVariables(variables);
        return tab;
    }

    public static Tab getTabGetAllStationsQueryWithoutVariables() {
        Tab tab = new Tab();
        tab.setName("Get all stations query");
        tab.setQuery("classpath:graphql/get-all-stations-query.graphql");
        return tab;
    }

    public static Tab getTabGetOperatingAreaQuery() {
        Tab tab = new Tab();
        tab.setName("Get operating area query");
        tab.setQuery("classpath:graphql/get-polygon-query.graphql");
        tab.setBufferInMetres(0L);
        return tab;
    }
}