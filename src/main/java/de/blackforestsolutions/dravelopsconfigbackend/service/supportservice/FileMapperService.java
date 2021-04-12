package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileMapperService{
    <T> T mapYamlWith(File file, Class<T> returnType) throws IOException;

    <T> T mapJsonWith(File file, Class<T> returnType) throws IOException;

    <T> String mapObjectToYaml(T object) throws JsonProcessingException;

    <T> String mapObjectToJson(T object) throws JsonProcessingException;
}