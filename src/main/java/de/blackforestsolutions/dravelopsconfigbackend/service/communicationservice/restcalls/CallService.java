package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface CallService{
    ResponseEntity<String> post(String url, HttpEntity<?> requestEntity);

    ResponseEntity<String> get(String url, HttpEntity<?> requestEntity);
}
