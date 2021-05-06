package de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling;

public class RefreshExecutionException extends RuntimeException {
    private static final long serialVersionUID = -2040729626241143822L;
    public RefreshExecutionException() {
        super("Refresh Event was not executed correctly: ");
    }
}