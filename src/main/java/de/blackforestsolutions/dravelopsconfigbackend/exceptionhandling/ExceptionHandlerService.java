package de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling;

import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import reactor.core.publisher.Mono;

public interface ExceptionHandlerService{

    <T> Mono<T> handleExceptions(CallStatus<T> callStatus);

}