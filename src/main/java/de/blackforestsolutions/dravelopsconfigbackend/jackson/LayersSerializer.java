package de.blackforestsolutions.dravelopsconfigbackend.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

public class LayersSerializer extends JsonSerializer<List<String>> {

    @Override
    public void serialize(List<String> layers, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeString(StringUtils.join(layers, ","));
    }
}