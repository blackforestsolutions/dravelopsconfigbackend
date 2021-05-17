package de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling;

import java.util.List;

public interface ExceptionHandlerService {
    <T> List<T> handleExceptions(Throwable exception);
}
