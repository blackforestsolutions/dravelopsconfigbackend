package de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling;

import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionHandlerServiceTest {

    private final ExceptionHandlerService classUnderTest = new ExceptionHandlerServiceImpl();

    @Test
    void test_handleExceptions_with_exception_returns_an_empty_list() {
        Exception testData = new Exception();

        List<CallStatus<GraphQlTab>> result = classUnderTest.handleExceptions(testData);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void test_handleExceptions_with_exception_as_null_returns_an_empty_list() {
        Exception testData = null;

        List<CallStatus<GraphQlTab>> result = classUnderTest.handleExceptions(testData);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void test_handleResponseEntityException_with_exception_returns_empty_ResponseEntity_with_HttpStatus_INTERNAL_SERVER_ERROR() {
        Exception testData = new Exception();

        ResponseEntity<Object> result = classUnderTest.handleResponseEntityException(testData);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void test_handleResponseEntityException_with_exception_as_null_returns_empty_ResponseEntity_with_HttpStatus_INTERNAL_SERVER_ERROR() {
        Exception testData = null;

        ResponseEntity<Object> result = classUnderTest.handleResponseEntityException(testData);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void test_handleResponseEntityExceptions_with_exception_returns_empty_ResponseEntity_with_HttpStatus_INTERNAL_SERVER_ERROR() {
        Exception testData = new Exception();

        ResponseEntity<List<Object>> result = classUnderTest.handleResponseEntityExceptions(testData);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEmpty();
    }

    @Test
    void test_handleResponseEntityExceptions_with_exception_as_null_returns_empty_ResponseEntity_with_HttpStatus_INTERNAL_SERVER_ERROR() {
        Exception testData = null;

        ResponseEntity<List<Object>> result = classUnderTest.handleResponseEntityExceptions(testData);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(result.getBody()).isEmpty();
    }
}
