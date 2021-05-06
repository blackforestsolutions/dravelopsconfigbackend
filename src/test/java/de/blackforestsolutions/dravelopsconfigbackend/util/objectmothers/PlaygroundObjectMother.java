package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Editor;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Playground;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Settings;

import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.EditorObjectMother.getEditorLightTheme;
import static de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers.SettingsObjectMother.getSettingsWithEditor;

public class PlaygroundObjectMother{

    public static Playground getPlaygroundForSuedbadenApi() {
        Editor editor = getEditorLightTheme();
        Settings settings = getSettingsWithEditor(editor);

        Playground playground = new Playground();
        playground.setPageTitle("Suedbaden-Api");
        playground.setSettings(settings);
        playground.setTabs(TabObjectMother.getTabsWithNoEmptyField());
        return playground;
    }
}