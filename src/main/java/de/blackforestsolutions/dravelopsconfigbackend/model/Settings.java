
package de.blackforestsolutions.dravelopsconfigbackend.model;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "editor"
})
public class Settings implements Serializable{

    @JsonProperty("editor")
    private Editor editor;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    private final static long serialVersionUID = 6949168463672915550L;

    @JsonProperty("editor")
    public Editor getEditor() {
        return editor;
    }

    @JsonProperty("editor")
    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}