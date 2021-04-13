
package de.blackforestsolutions.dravelopsconfigbackend.model;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "query",
    "variables",
    "maxResults",
    "departurePlaceholder",
    "arrivalPlaceholder",
    "maxPastDaysInCalendar",
    "layers",
    "bufferInMetres"
})
public class Tab implements Serializable {

    @JsonProperty("name")
    private String name;
    @JsonProperty("query")
    private String query;
    @JsonProperty("variables")
    private Variables variables;
    @JsonProperty("maxResults")
    private Long maxResults;
    @JsonProperty("departurePlaceholder")
    private String departurePlaceholder;
    @JsonProperty("arrivalPlaceholder")
    private String arrivalPlaceholder;
    @JsonProperty("maxPastDaysInCalendar")
    private Long maxPastDaysInCalendar;
    @JsonProperty("layers")
    private String layers;
    @JsonProperty("bufferInMetres")
    private Long bufferInMetres;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    private final static long serialVersionUID = -5534681326926075973L;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("query")
    public String getQuery() {
        return query;
    }

    @JsonProperty("query")
    public void setQuery(String query) {
        this.query = query;
    }

    @JsonProperty("variables")
    public Variables getVariables() {
        return variables;
    }

    @JsonProperty("variables")
    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    @JsonProperty("maxResults")
    public Long getMaxResults() {
        return maxResults;
    }

    @JsonProperty("maxResults")
    public void setMaxResults(Long maxResults) {
        this.maxResults = maxResults;
    }

    @JsonProperty("departurePlaceholder")
    public String getDeparturePlaceholder() {
        return departurePlaceholder;
    }

    @JsonProperty("departurePlaceholder")
    public void setDeparturePlaceholder(String departurePlaceholder) {
        this.departurePlaceholder = departurePlaceholder;
    }

    @JsonProperty("arrivalPlaceholder")
    public String getArrivalPlaceholder() {
        return arrivalPlaceholder;
    }

    @JsonProperty("arrivalPlaceholder")
    public void setArrivalPlaceholder(String arrivalPlaceholder) {
        this.arrivalPlaceholder = arrivalPlaceholder;
    }

    @JsonProperty("maxPastDaysInCalendar")
    public Long getMaxPastDaysInCalendar() {
        return maxPastDaysInCalendar;
    }

    @JsonProperty("maxPastDaysInCalendar")
    public void setMaxPastDaysInCalendar(Long maxPastDaysInCalendar) {
        this.maxPastDaysInCalendar = maxPastDaysInCalendar;
    }

    @JsonProperty("layers")
    public String getLayers() {
        return layers;
    }

    @JsonProperty("layers")
    public void setLayers(String layers) {
        this.layers = layers;
    }

    @JsonProperty("bufferInMetres")
    public Long getBufferInMetres() {
        return bufferInMetres;
    }

    @JsonProperty("bufferInMetres")
    public void setBufferInMetres(Long bufferInMetres) {
        this.bufferInMetres = bufferInMetres;
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