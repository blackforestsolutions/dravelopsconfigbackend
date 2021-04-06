package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsconfigbackend.model.*;

import java.util.ArrayList;
import java.util.List;

public class GraphQLApiConfigObjectMother {

    public static GraphQLApiConfig getGraphQLApiConfig(){
        GraphQLApiConfig graphQLApiConfig = new GraphQLApiConfig();
        Graphql graphql = new Graphql();

        Playground playground = new Playground();
        playground.setPageTitle("Suedbaden-Api");

        Editor editor = new Editor();
        editor.setTheme("light");

        Settings settings = new Settings();
        settings.setEditor(editor);
        playground.setSettings(settings);

        List<Tab> tabList = new ArrayList<>();

        Tab tab = new Tab();
        tab.setName("Journey query");
        tab.setQuery("classpath:playground/requests/get-journeys-query-max-parameters.graphql");
        tab.setMaxResults(5L);
        tab.setDeparturePlaceholder("Start");
        tab.setArrivalPlaceholder("Ziel");
        tab.setMaxPastDaysInCalendar(0L);

        Variables variables = new Variables();
        variables.setDepartureLatitude(48.048381d);
        variables.setDepartureLongitude(8.209198d);
        variables.setArrivalLatitude(48.087517d);
        variables.setArrivalLongitude(7.891595d);
        variables.setDateTime("2021-03-17T13:00:00+02:00");
        variables.setIsArrivalDateTime(false);
        variables.setLanguage("de");
        tab.setVariables(variables);

        tabList.add(tab);

        tab = new Tab();
        tab.setName("Journey subscription");
        tab.setQuery("classpath:playground/requests/get-journeys-subscription-max-parameters.graphql");
        tab.setMaxResults(5L);
        tab.setDeparturePlaceholder("Start");
        tab.setArrivalPlaceholder("Ziel");
        tab.setMaxPastDaysInCalendar(0L);

        variables = new Variables();
        variables.setDepartureLatitude(48.048381d);
        variables.setDepartureLongitude(8.209198d);
        variables.setArrivalLatitude(48.087517d);
        variables.setDateTime("2021-03-17T13:00:00+02:00");
        variables.setIsArrivalDateTime(false);
        variables.setLanguage("de");

        tab.setVariables(variables);
        tabList.add(tab);


        tab = new Tab();
        tab.setName("Autocomplete addresses query");
        tab.setQuery("classpath:playground/requests/get-autocomplete-addresses-query-max-parameters.graphql");
        tab.setMaxResults(10L);
        tab.setLayers("venue, address, street, locality\n");

        variables = new Variables();
        variables.setLanguage("de");
        variables.setText("Freiburg im Breisgau");

        tab.setVariables(variables);
        tabList.add(tab);


        tab = new Tab();
        tab.setName("Nearest addresses query");
        tab.setQuery("classpath:playground/requests/get-nearest-addresses-query-max-parameters.graphql");
        tab.setMaxResults(10L);
        tab.setLayers("venue, address, street, locality\n");

        variables = new Variables();
        variables.setLanguage("de");
        variables.setLongitude(8.209198d);
        variables.setLatitude(48.048381d);
        variables.setRadiusInKilometers(1.0d);

        tab.setVariables(variables);
        tabList.add(tab);


        tab = new Tab();
        tab.setName("Nearest stations query");
        tab.setQuery("classpath:playground/requests/get-nearest-stations-query-max-parameters.graphql");
        tab.setMaxResults(10L);

        variables = new Variables();
        variables.setLanguage("de");
        variables.setLongitude(8.209198d);
        variables.setLatitude(48.048381d);
        variables.setRadiusInKilometers(1.0d);

        tab.setVariables(variables);
        tabList.add(tab);


        tab = new Tab();
        tab.setName("Get all stations query");
        tab.setQuery("classpath:graphql/get-all-stations-query.graphql");

        tabList.add(tab);

        tab = new Tab();
        tab.setName("Get operating area query");
        tab.setQuery("classpath:graphql/get-polygon-query.graphql");
        tab.setBufferInMetres(2000L);

        tabList.add(tab);

        playground.setTabs(tabList);
        graphql.setPlayground(playground);
        graphQLApiConfig.setGraphql(graphql);
        return graphQLApiConfig;
    }
}