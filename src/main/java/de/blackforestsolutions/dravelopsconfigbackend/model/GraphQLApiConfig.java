
package de.blackforestsolutions.dravelopsconfigbackend.model;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "graphql"
})
public class GraphQLApiConfig implements Serializable {

    @JsonProperty("graphql")
    private Graphql graphql;
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<>();
    private final static long serialVersionUID = -8595616181418124189L;

    @JsonProperty("graphql")
    public Graphql getGraphql() {
        return graphql;
    }

    @JsonProperty("graphql")
    public void setGraphql(Graphql graphql) {
        this.graphql = graphql;
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