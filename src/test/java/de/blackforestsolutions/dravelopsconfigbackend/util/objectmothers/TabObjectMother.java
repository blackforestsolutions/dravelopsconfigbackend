package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsconfigbackend.model.Tab;
import de.blackforestsolutions.dravelopsconfigbackend.model.Variables;

/**
 * Possible Fields:
 "name",
 "query",
 "variables",
 "maxResults",
 "departurePlaceholder",
 "arrivalPlaceholder",
 "maxPastDaysInCalendar",
 "layers",
 "bufferInMetres"
 */
public class TabObjectMother {

    public static Tab getTabJourneyQuery(Variables variables){
        return Tab.builder()
                .name("Journey query")
                .query("classpath:playground/requests/get-journeys-query-max-parameters.graphql")
                .maxResults(5L)
                .departurePlaceholder("Start")
                .arrivalPlaceholder("Ziel")
                .maxPastDaysInCalendar(0L)
                .variables(variables)
                .build();
    }

    public static Tab getTabJourneySubscription(Variables variables){
        return Tab.builder()
                .name("Journey subscription")
                .query("classpath:playground/requests/get-journeys-subscription-max-parameters.graphql")
                .maxResults(5L)
                .departurePlaceholder("Start")
                .arrivalPlaceholder("Ziel")
                .maxPastDaysInCalendar(0L)
                .variables(variables)
                .build();
    }

    public static Tab getTabAutocompleteAddressesQuery(Variables variables){
        return Tab.builder()
                .name("Autocomplete addresses query")
                .query("classpath:playground/requests/get-autocomplete-addresses-query-max-parameters.graphql")
                .maxResults(10L)
                .layers("venue, address, street, locality\n")
                .variables(variables)
                .build();
    }

    public static Tab getTabNearestAddressesQuery(Variables variables){
        return Tab.builder()
                .name("Nearest addresses query")
                .query("classpath:playground/requests/get-nearest-addresses-query-max-parameters.graphql")
                .maxResults(10L)
                .layers("venue, address, street, locality\n")
                .variables(variables)
                .build();
    }

    public static Tab getTabNearestStationsQuery(Variables variables){
        return Tab.builder()
                .name("Nearest stations query")
                .query("classpath:playground/requests/get-nearest-stations-query-max-parameters.graphql")
                .maxResults(10L)
                .variables(variables)
                .build();
    }

    public static Tab getTabGetAllStationsQueryWithoutVariables(){
        return Tab.builder()
                .name("Get all stations query")
                .query("classpath:graphql/get-all-stations-query.graphql")
                .build();
    }

    public static Tab getTabGetOperatingAreaQuery(){
        return Tab.builder()
                .name("Get operating area query")
                .query("classpath:graphql/get-polygon-query.graphql")
                .bufferInMetres(2000L)
                .build();
    }
}