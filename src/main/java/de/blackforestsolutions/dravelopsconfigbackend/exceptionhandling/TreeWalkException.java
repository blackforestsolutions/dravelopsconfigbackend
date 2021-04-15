package de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling;

public class TreeWalkException extends RuntimeException{

    public TreeWalkException() {
        super("Error in TreeWalker. Given path is not an endpoint in the repository");
    }

}