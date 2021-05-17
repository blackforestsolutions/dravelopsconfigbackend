package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Settings;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.EditorObjectMother.getLightEditorTheme;

public class SettingsObjectMother {

    public static Settings getSettingsWithNoEmptyFields() {
        Settings settings = new Settings();
        settings.setEditor(getLightEditorTheme());
        return settings;
    }
}