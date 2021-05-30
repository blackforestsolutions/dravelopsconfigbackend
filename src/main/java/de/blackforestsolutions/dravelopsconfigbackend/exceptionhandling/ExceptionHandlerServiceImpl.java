package de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ExceptionHandlerServiceImpl implements ExceptionHandlerService {

    @Override
    public <T> List<T> handleExceptions(Throwable exception) {
        if (Optional.ofNullable(exception).isEmpty()) {
            logMissingException();
            return Collections.emptyList();
        }
        logError(exception);
        return Collections.emptyList();
    }

    @Override
    public <T> ResponseEntity<T> handleResponseEntityException(Throwable exception) {
        if (Optional.ofNullable(exception).isEmpty()) {
            logMissingException();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logError(exception);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public <T> ResponseEntity<List<T>> handleResponseEntityExceptions(Throwable exception) {
        if (Optional.ofNullable(exception).isEmpty()) {
            logMissingException();
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logError(exception);
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static void logError(Throwable e) {
        log.error("Internal Server Error: ", e);
    }

    private static void logMissingException() {
        log.warn("No Exception available!");
    }
}