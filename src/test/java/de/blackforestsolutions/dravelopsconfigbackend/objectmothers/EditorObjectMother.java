package de.blackforestsolutions.dravelopsconfigbackend.objectmothers;

import de.blackforestsolutions.dravelopsgeneratedcontent.graphql.Editor;

public class EditorObjectMother {

    public static Editor getLightEditorTheme() {
        Editor editor = new Editor();
        editor.setTheme("light");
        return editor;
    }

    public static Editor getDarkEditorTheme() {
        Editor editor = new Editor();
        editor.setTheme("dark");
        return editor;
    }
}