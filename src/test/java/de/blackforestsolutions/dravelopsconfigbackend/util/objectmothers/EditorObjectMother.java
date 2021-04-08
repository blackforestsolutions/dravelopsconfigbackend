package de.blackforestsolutions.dravelopsconfigbackend.util.objectmothers;

import de.blackforestsolutions.dravelopsconfigbackend.model.Editor;

public class EditorObjectMother {

    public static Editor getEditorLightTheme(){
        return Editor.builder()
                .theme("light")
                .build();
    }

    public static Editor getDarkEditorTheme(){
        return Editor.builder()
                .theme("dark")
                .build();
    }
}