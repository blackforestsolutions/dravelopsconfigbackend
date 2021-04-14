
package de.blackforestsolutions.dravelopsconfigbackend.model;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "theme"
})
public class Editor implements Serializable{

    @JsonProperty("theme")
    private String theme;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    private final static long serialVersionUID = 3820088329272205950L;

    @JsonProperty("theme")
    public String getTheme() {
        return theme;
    }

    @JsonProperty("theme")
    public void setTheme(String theme) {
        this.theme = theme;
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