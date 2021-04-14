package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsconfigbackend.model.Editor;
import de.blackforestsolutions.dravelopsconfigbackend.model.Playground;
import de.blackforestsolutions.dravelopsconfigbackend.model.Settings;
import de.blackforestsolutions.dravelopsconfigbackend.model.Tab;

import java.util.ArrayList;
import java.util.List;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.EditorObjectMother.getEditorLightTheme;
import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.SettingsObjectMother.getSettingsWithEditor;
import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.TabObjectMother.*;
import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.VariablesObjectMother.*;

public class PlaygroundObjectMother{


    public static Playground getPlaygroundForSuedbadenApi() {
        Editor editor = getEditorLightTheme();
        Settings settings = getSettingsWithEditor(editor);

        List<Tab> tabList = new ArrayList<>();
        tabList.add(getTabJourneyQuery(getVariablesWithAllInformation()));
        tabList.add(getTabJourneySubscription(getVariablesWithoutArrivalLongitude()));
        tabList.add(getTabAutocompleteAddressesQuery(getVariablesWithLanguageAndText()));
        tabList.add(getTabNearestAddressesQuery(getVariablesWithLanguageLongitudeLatitudeRadius()));
        tabList.add(getTabNearestStationsQuery(getVariablesWithLanguageLongitudeLatitudeRadius()));
        tabList.add(getTabGetAllStationsQueryWithoutVariables());
        tabList.add(getTabGetOperatingAreaQuery());

        Playground playground = new Playground();
        playground.setPageTitle("Suedbaden-Api");
        playground.setSettings(settings);
        playground.setTabs(tabList);
        return playground;
    }
}