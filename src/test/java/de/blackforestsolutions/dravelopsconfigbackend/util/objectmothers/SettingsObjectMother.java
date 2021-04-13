package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsconfigbackend.model.Editor;
import de.blackforestsolutions.dravelopsconfigbackend.model.Settings;

public class SettingsObjectMother {

    public static Settings getSettingsWithEditor(Editor editor){
        Settings settings = new Settings();
        settings.setEditor(editor);
        return settings;
    }
}