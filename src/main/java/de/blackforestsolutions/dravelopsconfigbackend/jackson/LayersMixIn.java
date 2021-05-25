package de.blackforestsolutions.dravelopsconfigbackend.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public abstract class LayersMixIn {

    /**
     * This field could potentially be unused at compilation time.
     * It is internally called by Jackson via reflection.
     */
    @SuppressWarnings("unused")
    @JsonProperty("layers")
    @JsonSerialize(using = LayersSerializer.class)
    @JsonDeserialize(using = LayersDeserializer.class)
    private List<String> layers;


    /**
     * This constructor could potentially be unused at compilation time.
     * It is internally called by Jackson via reflection.
     */
    @SuppressWarnings("unused")
    @JsonCreator
    public LayersMixIn(@JsonProperty("layers") List<String> layers) {

    }

}
