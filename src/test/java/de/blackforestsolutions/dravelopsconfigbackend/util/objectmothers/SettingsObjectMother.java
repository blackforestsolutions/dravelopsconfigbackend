package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Editor;
import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Settings;

public class SettingsObjectMother{

    public static Settings getSettingsWithEditor(Editor editor) {
        Settings settings = new Settings();
        settings.setEditor(editor);
        return settings;
    }
}