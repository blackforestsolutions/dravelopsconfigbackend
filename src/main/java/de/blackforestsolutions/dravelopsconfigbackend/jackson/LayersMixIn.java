package de.blackforestsolutions.dravelopsconfigbackend.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public abstract class LayersMixIn {

    @JsonProperty("layers")
    @JsonSerialize(using = LayersSerializer.class)
    @JsonDeserialize(using = LayersDeserializer.class)
    List<String> layers;

    @JsonCreator
    public LayersMixIn(@JsonProperty("layers") List<String> layers) {

    }

}
