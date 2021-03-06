package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CallServiceImpl implements CallService {

    private final RestTemplate restTemplate;

    @Autowired
    public CallServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public ResponseEntity<String> put(String url, HttpEntity<?> requestEntity) {
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
    }

    @Override
    public ResponseEntity<String> post(String url, HttpEntity<?> requestEntity) {
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    @Override
    public ResponseEntity<String> get(String url, HttpEntity<?> requestEntity) {
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }
}