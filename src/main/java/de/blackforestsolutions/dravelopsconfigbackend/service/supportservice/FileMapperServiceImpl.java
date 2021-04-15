package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import de.blackforestsolutions.dravelopsdatamodel.util.DravelOpsJsonMapper;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileMapperServiceImpl implements FileMapperService{

    private static final String TEMP_YAML_FILE_NAME = "temp_yaml_file";
    private static final String YAML_SUFFIX = ".yaml";
    private static final String TEMP_JSON_FILE_NAME = "temp_json_file";
    private static final String JSON_SUFFIX = ".json";

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
    public <T> File mapObjectToYaml(T object) throws IOException {
        DravelOpsJsonMapper om = new DravelOpsJsonMapper();
        JsonNode jsonNode = om.readTree(om.writeValueAsString(object));

        File tempFile = Files.createTempFile(TEMP_YAML_FILE_NAME, YAML_SUFFIX).toFile();
        PrintWriter writer = new PrintWriter(tempFile);
        writer.write(new YAMLMapper().writeValueAsString(jsonNode));
        writer.close();
        return tempFile;
    }

    @Override
    public <T> File mapObjectToJson(T object) throws IOException {
        File tempFile = Files.createTempFile(TEMP_JSON_FILE_NAME, JSON_SUFFIX).toFile();
        PrintWriter writer = new PrintWriter(tempFile);
        writer.write(new DravelOpsJsonMapper().writeValueAsString(object));
        writer.close();
        return tempFile;
    }
}