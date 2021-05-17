package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Variables;

public class VariablesObjectMother {

    private static final String DEFAULT_DATETIME = "2020-09-30T13:00+02:00";
    private static final double DEFAULT_DEPARTURE_LATITUDE = 48.048381d;
    private static final double DEFAULT_DEPARTURE_LONGITUDE = 8.209198d;
    private static final double DEFAULT_ARRIVAL_LATITUDE = 48.087517d;
    private static final double DEFAULT_ARRIVAL_LONGITUDE = 7.891595d;
    private static final String DEFAULT_LANGUAGE = "de";

    public static Variables getJourneyVariables() {
        Variables variables = new Variables();

        variables.setDepartureLatitude(DEFAULT_DEPARTURE_LATITUDE);
        variables.setDepartureLongitude(DEFAULT_DEPARTURE_LONGITUDE);
        variables.setArrivalLatitude(DEFAULT_ARRIVAL_LATITUDE);
        variables.setArrivalLongitude(DEFAULT_ARRIVAL_LONGITUDE);
        variables.setDateTime(DEFAULT_DATETIME);
        variables.setIsArrivalDateTime(false);
        variables.setLanguage(DEFAULT_LANGUAGE);

        return variables;
    }

    public static Variables getAddressAutocompletionQueryVariables() {
        Variables variables = new Variables();

        variables.setLanguage(DEFAULT_LANGUAGE);
        variables.setText("Am Großhausberg 8");

        return variables;
    }

    public static Variables getNearestAddressesQueryVariables() {
        Variables variables = new Variables();

        variables.setLanguage(DEFAULT_LANGUAGE);
        variables.setLatitude(DEFAULT_ARRIVAL_LATITUDE);
        variables.setLongitude(DEFAULT_ARRIVAL_LONGITUDE);
        variables.setRadiusInKilometers(1.0d);

        return variables;
    }

    public static Variables getNearestStationsQueryVariables() {
        Variables variables = new Variables();

        variables.setLanguage(DEFAULT_LANGUAGE);
        variables.setLatitude(DEFAULT_DEPARTURE_LATITUDE);
        variables.setLongitude(DEFAULT_DEPARTURE_LONGITUDE);
        variables.setRadiusInKilometers(1.0d);

        return variables;
    }
}