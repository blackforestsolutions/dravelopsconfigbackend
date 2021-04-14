
package de.blackforestsolutions.dravelopsconfigbackend.model;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "page-title",
        "settings",
        "tabs"
})
public class Playground implements Serializable{

    @JsonProperty("page-title")
    private String pageTitle;
    @JsonProperty("settings")
    private Settings settings;
    @JsonProperty("tabs")
    private List<Tab> tabs = null;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    private final static long serialVersionUID = - 6657780933993216193L;

    @JsonProperty("page-title")
    public String getPageTitle() {
        return pageTitle;
    }

    @JsonProperty("page-title")
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @JsonProperty("settings")
    public Settings getSettings() {
        return settings;
    }

    @JsonProperty("settings")
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @JsonProperty("tabs")
    public List<Tab> getTabs() {
        return tabs;
    }

    @JsonProperty("tabs")
    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
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