package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Tab;

import java.util.LinkedHashMap;
import java.util.Map;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.LayersObjectMother.getAddressAutocompletionQueryLayers;
import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.LayersObjectMother.getNearestAddressesQueryLayers;
import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.VariablesObjectMother.*;

public class TabObjectMother {

    private static final String DEFAULT_ARRIVAL_PLACEHOLDER = "Ziel";
    private static final String DEFAULT_DEPARTURE_PLACEHOLDER = "Start";
    private static final long DEFAULT_MAX_PAST_DAYS_IN_CALENDAR = 0L;

    public static Map<String, Tab> getTabsWithNoEmptyField() {
        Map<String, Tab> tabMap = new LinkedHashMap<>();

        tabMap.put(GraphQlTab.JOURNEY_QUERY.toString(), getTabJourneyQuery());
        tabMap.put(GraphQlTab.JOURNEY_SUBSCRIPTION.toString(), getTabJourneySubscription());
        tabMap.put(GraphQlTab.ADDRESS_AUTOCOMPLETION.toString(), getTabAutocompleteAddressesQuery());
        tabMap.put(GraphQlTab.NEAREST_ADDRESSES.toString(), getTabNearestAddressesQuery());
        tabMap.put(GraphQlTab.NEAREST_STATIONS.toString(), getTabNearestStationsQuery());
        tabMap.put(GraphQlTab.ALL_STATIONS.toString(), getTabGetAllStationsQueryWithoutVariables());
        tabMap.put(GraphQlTab.OPERATING_AREA.toString(), getTabGetOperatingAreaQuery());

        return tabMap;
    }

    public static Map<String, Tab> getTabsWithEmptyJourneySubscription() {
        Map<String, Tab> tabMap = new LinkedHashMap<>();

        tabMap.put(GraphQlTab.JOURNEY_QUERY.toString(), getTabJourneyQuery());
        tabMap.put(GraphQlTab.JOURNEY_SUBSCRIPTION.toString(), getTabEmptyJourneySubscription());
        tabMap.put(GraphQlTab.ADDRESS_AUTOCOMPLETION.toString(), getTabAutocompleteAddressesQuery());
        tabMap.put(GraphQlTab.NEAREST_ADDRESSES.toString(), getTabNearestAddressesQuery());
        tabMap.put(GraphQlTab.NEAREST_STATIONS.toString(), getTabNearestStationsQuery());
        tabMap.put(GraphQlTab.ALL_STATIONS.toString(), getTabGetAllStationsQueryWithoutVariables());
        tabMap.put(GraphQlTab.OPERATING_AREA.toString(), getTabGetOperatingAreaQuery());

        return tabMap;
    }

    public static Tab getTabEmptyJourneySubscription() {
        Tab tab = new Tab();

        tab.setName("Journey subscription");

        return tab;
    }

    public static Tab getTabJourneyQuery() {
        Tab tab = new Tab();

        tab.setName("Journey query");
        tab.setQuery("classpath:playground/requests/get-journeys-query-max-parameters.graphql");
        tab.setMaxResults(5L);
        tab.setDeparturePlaceholder(DEFAULT_DEPARTURE_PLACEHOLDER);
        tab.setArrivalPlaceholder(DEFAULT_ARRIVAL_PLACEHOLDER);
        tab.setMaxPastDaysInCalendar(DEFAULT_MAX_PAST_DAYS_IN_CALENDAR);
        tab.setVariables(getJourneyVariables());

        return tab;
    }

    public static Tab getTabJourneySubscription() {
        Tab tab = new Tab();

        tab.setName("Journey subscription");
        tab.setQuery("classpath:playground/requests/get-journeys-subscription-max-parameters.graphql");
        tab.setMaxResults(5L);
        tab.setDeparturePlaceholder(DEFAULT_DEPARTURE_PLACEHOLDER);
        tab.setArrivalPlaceholder(DEFAULT_ARRIVAL_PLACEHOLDER);
        tab.setMaxPastDaysInCalendar(DEFAULT_MAX_PAST_DAYS_IN_CALENDAR);
        tab.setVariables(getJourneyVariables());

        return tab;
    }

    public static Tab getTabAutocompleteAddressesQuery() {
        Tab tab = new Tab();

        tab.setName("Autocomplete addresses query");
        tab.setQuery("classpath:playground/requests/get-autocomplete-addresses-query-max-parameters.graphql");
        tab.setMaxResults(10L);
        tab.setLayers(getAddressAutocompletionQueryLayers());
        tab.setVariables(getAddressAutocompletionQueryVariables());

        return tab;
    }

    public static Tab getTabNearestAddressesQuery() {
        Tab tab = new Tab();

        tab.setName("Nearest addresses query");
        tab.setQuery("classpath:playground/requests/get-nearest-addresses-query-max-parameters.graphql");
        tab.setMaxResults(10L);
        tab.setLayers(getNearestAddressesQueryLayers());
        tab.setVariables(getNearestAddressesQueryVariables());

        return tab;
    }

    public static Tab getTabNearestStationsQuery() {
        Tab tab = new Tab();

        tab.setName("Nearest stations query");
        tab.setQuery("classpath:playground/requests/get-nearest-stations-query-max-parameters.graphql");
        tab.setMaxResults(10L);
        tab.setVariables(getNearestStationsQueryVariables());

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