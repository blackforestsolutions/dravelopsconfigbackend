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


    public static Variables getVariablesWithAllInformation(){
        return Variables.builder()
                .departureLatitude(48.048381d)
                .departureLongitude(8.209198d)
                .arrivalLatitude(48.087517d)
                .arrivalLongitude(7.891595d)
                .dateTime("2021-03-17T13:00:00+02:00")
                .isArrivalDateTime(false)
                .language("de")
                .build();
    }

    public static Variables getVariablesWithoutArrivalLongitude(){
        return Variables.builder()
                .departureLatitude(48.048381d)
                .departureLongitude(8.209198d)
                .arrivalLatitude(48.087517d)
                .dateTime("2021-03-17T13:00:00+02:00")
                .isArrivalDateTime(false)
                .language("de")
                .build();
    }

    public static Variables getVariablesWithLanguageAndText(){
        return Variables.builder()
                .language("de")
                .text("Freiburg im Breisgau")
                .build();
    }

    public static Variables getVariablesWithLanguageLongitudeLatitudeRadius(){
        return Variables.builder()
                .language("de")
                .longitude(8.209198d)
                .latitude(48.048381d)
                .radiusInKilometers(1.0d)
                .build();
    }
}