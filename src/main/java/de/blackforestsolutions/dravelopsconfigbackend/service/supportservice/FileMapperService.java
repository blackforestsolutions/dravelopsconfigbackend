package de.blackforestsolutions.dravelopsconfigbackend.service.supportservice;

import java.io.File;
import java.io.IOException;

public interface FileMapperService{
    <T> T mapYamlWith(File file, Class<T> returnType) throws IOException;

    <T> T mapJsonWith(File file, Class<T> returnType) throws IOException;

    <T> File mapObjectToYaml(T object) throws IOException;

    <T> File mapObjectToJson(T object) throws IOException;
}