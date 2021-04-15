package de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling;

public class RevCommitFetchingException extends RuntimeException{

    public RevCommitFetchingException() {
        super("Error in fetching RevCommit from GitHub");
    }

}