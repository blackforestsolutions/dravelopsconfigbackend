
package de.blackforestsolutions.dravelopsconfigbackend.model;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "playground"
})
public class Graphql implements Serializable{

    @JsonProperty("playground")
    private Playground playground;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    private final static long serialVersionUID = - 8393944921223988383L;

    @JsonProperty("playground")
    public Playground getPlayground() {
        return playground;
    }

    @JsonProperty("playground")
    public void setPlayground(Playground playground) {
        this.playground = playground;
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