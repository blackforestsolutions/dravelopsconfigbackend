package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsconfigbackend.model.Tab;
import de.blackforestsolutions.dravelopsconfigbackend.model.Variables;

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
    private static final String DEFAULT_LAYERS = "venue, address, street, locality\n";

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
        tab.setBufferInMetres(2000L);
        return tab;
    }
}