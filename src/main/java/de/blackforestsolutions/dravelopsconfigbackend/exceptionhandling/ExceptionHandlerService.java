package de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExceptionHandlerService {

    <T> List<T> handleExceptions(Throwable exception);

    <T> ResponseEntity<T> handleResponseEntityException(Throwable exception);

    <T> ResponseEntity<List<T>> handleResponseEntityExceptions(Throwable exception);

}