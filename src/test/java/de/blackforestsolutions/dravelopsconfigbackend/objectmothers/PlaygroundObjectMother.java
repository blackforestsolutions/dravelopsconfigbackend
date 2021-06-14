package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Playground;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.SettingsObjectMother.getSettingsWithNoEmptyFields;
import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.TabObjectMother.getTabsWithEmptyJourneySubscription;
import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.TabObjectMother.getTabsWithNoEmptyField;

public class PlaygroundObjectMother {

    public static Playground getPlaygroundWithNoEmptyFields() {
        Playground playground = new Playground();

        playground.setPageTitle("Suedbaden-Api");
        playground.setSettings(getSettingsWithNoEmptyFields());
        playground.setTabs(getTabsWithNoEmptyField());

        return playground;
    }


    public static Playground getPlayGroundWithEmptyJourneySubscriptionTab() {
        Playground playground = new Playground();

        playground.setPageTitle("Suedbaden-Api");
        playground.setSettings(getSettingsWithNoEmptyFields());
        playground.setTabs(getTabsWithEmptyJourneySubscription());


        return playground;
    }
}