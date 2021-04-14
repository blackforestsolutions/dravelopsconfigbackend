package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsJsonMapper;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileMapperServiceImpl implements FileMapperService{

    @Override
    public <T> T mapYamlWith(File file, Class<T> returnType) throws IOException {
        String fileContent = Files.readString(Path.of(file.getPath()), StandardCharsets.UTF_8);
        DravelOpsJsonMapper om = new DravelOpsJsonMapper();
        String jsonString = om.writeValueAsString(new Yaml().load(fileContent));
        return om.readValue(jsonString, returnType);
    }

    @Override
    public <T> T mapJsonWith(File file, Class<T> returnType) throws IOException {
        String fileContent = Files.readString(Path.of(file.getPath()), StandardCharsets.UTF_8);
        return new DravelOpsJsonMapper().readValue(fileContent, returnType);
    }

    @Override
    public <T> String mapObjectToYaml(T object) throws JsonProcessingException {
        DravelOpsJsonMapper om = new DravelOpsJsonMapper();
        JsonNode jsonNode = om.readTree(om.writeValueAsString(object));
        return new YAMLMapper().writeValueAsString(jsonNode);
    }

    @Override
    public <T> String mapObjectToJson(T object) throws JsonProcessingException {
        return new DravelOpsJsonMapper().writeValueAsString(object);
    }
}