package de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling;

import lombok.extern.slf4j.Slf4j;
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

    private static void logMissingException() {
        log.warn("No Exception available!");
    }

    private static void logError(Throwable e) {
        log.error("Internal Server Error: ", e);
    }

}
