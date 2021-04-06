
package de.blackforestsolutions.dravelopsconfigbackend.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "departureLatitude",
    "departureLongitude",
    "arrivalLatitude",
    "arrivalLongitude",
    "dateTime",
    "isArrivalDateTime",
    "language",
    "text",
    "longitude",
    "latitude",
    "radiusInKilometers"
})
public class Variables implements Serializable {

    @JsonProperty("departureLatitude")
    private Double departureLatitude;
    @JsonProperty("departureLongitude")
    private Double departureLongitude;
    @JsonProperty("arrivalLatitude")
    private Double arrivalLatitude;
    @JsonProperty("arrivalLongitude")
    private Double arrivalLongitude;
    @JsonProperty("dateTime")
    private String dateTime;
    @JsonProperty("isArrivalDateTime")
    private Boolean isArrivalDateTime;
    @JsonProperty("language")
    private String language;
    @JsonProperty("text")
    private String text;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("radiusInKilometers")
    private Double radiusInKilometers;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    private final static long serialVersionUID = -1841458124451723067L;

    @JsonProperty("departureLatitude")
    public Double getDepartureLatitude() {
        return departureLatitude;
    }

    @JsonProperty("departureLatitude")
    public void setDepartureLatitude(Double departureLatitude) {
        this.departureLatitude = departureLatitude;
    }

    @JsonProperty("departureLongitude")
    public Double getDepartureLongitude() {
        return departureLongitude;
    }

    @JsonProperty("departureLongitude")
    public void setDepartureLongitude(Double departureLongitude) {
        this.departureLongitude = departureLongitude;
    }

    @JsonProperty("arrivalLatitude")
    public Double getArrivalLatitude() {
        return arrivalLatitude;
    }

    @JsonProperty("arrivalLatitude")
    public void setArrivalLatitude(Double arrivalLatitude) {
        this.arrivalLatitude = arrivalLatitude;
    }

    @JsonProperty("arrivalLongitude")
    public Double getArrivalLongitude() {
        return arrivalLongitude;
    }

    @JsonProperty("arrivalLongitude")
    public void setArrivalLongitude(Double arrivalLongitude) {
        this.arrivalLongitude = arrivalLongitude;
    }

    @JsonProperty("dateTime")
    public String getDateTime() {
        return dateTime;
    }

    @JsonProperty("dateTime")
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @JsonProperty("isArrivalDateTime")
    public Boolean getIsArrivalDateTime() {
        return isArrivalDateTime;
    }

    @JsonProperty("isArrivalDateTime")
    public void setIsArrivalDateTime(Boolean isArrivalDateTime) {
        this.isArrivalDateTime = isArrivalDateTime;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("radiusInKilometers")
    public Double getRadiusInKilometers() {
        return radiusInKilometers;
    }

    @JsonProperty("radiusInKilometers")
    public void setRadiusInKilometers(Double radiusInKilometers) {
        this.radiusInKilometers = radiusInKilometers;
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