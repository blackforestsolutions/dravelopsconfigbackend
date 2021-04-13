package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsconfigbackend.model.Variables;

/**
 * Possible Fields:
 "departureLatitude",
 "departureLongitude",
 "arrivalLatitude",
 "arrivalLongitude",
 "dateTime",
 "isArrivalDateTime",
 "language",
 "text",
 "longitude",
 "latitude",
 "radiusInKilometers"
 */
public class VariablesObjectMother{

    private static final String DEFAULT_DATETIME = "2021-03-17T13:00:00+02:00";
    private static final double DEFAULT_LATITUDE = 48.048381d;
    private static final double DEFAULT_LONGITUDE = 8.209198d;
    private static final double DEFAULT_ARRIVAL_LATITUDE = 48.087517d;
    private static final String DEFAULT_LANGUAGE = "de";

    public static Variables getVariablesWithAllInformation(){
        Variables variables = new Variables();
        variables.setDepartureLatitude(DEFAULT_LATITUDE);
        variables.setDepartureLongitude(DEFAULT_LONGITUDE);
        variables.setArrivalLatitude(DEFAULT_ARRIVAL_LATITUDE);
        variables.setArrivalLongitude(7.891595d);
        variables.setDateTime(DEFAULT_DATETIME);
        variables.setIsArrivalDateTime(false);
        variables.setLanguage(DEFAULT_LANGUAGE);
        return variables;
    }

    public static Variables getVariablesWithoutArrivalLongitude(){
        Variables variables = new Variables();
        variables.setDepartureLatitude(DEFAULT_LATITUDE);
        variables.setDepartureLongitude(DEFAULT_LONGITUDE);
        variables.setArrivalLatitude(DEFAULT_ARRIVAL_LATITUDE);
        variables.setDateTime(DEFAULT_DATETIME);
        variables.setIsArrivalDateTime(false);
        variables.setLanguage(DEFAULT_LANGUAGE);
        return variables;
    }

    public static Variables getVariablesWithLanguageAndText(){
        Variables variables = new Variables();
        variables.setLanguage(DEFAULT_LANGUAGE);
        variables.setText("Freiburg im Breisgau");
        return variables;
    }

    public static Variables getVariablesWithLanguageLongitudeLatitudeRadius(){
        Variables variables = new Variables();
        variables.setLanguage(DEFAULT_LANGUAGE);
        variables.setLatitude(DEFAULT_LATITUDE);
        variables.setLongitude(DEFAULT_LONGITUDE);
        variables.setRadiusInKilometers(1.0d);
        return variables;
    }
}