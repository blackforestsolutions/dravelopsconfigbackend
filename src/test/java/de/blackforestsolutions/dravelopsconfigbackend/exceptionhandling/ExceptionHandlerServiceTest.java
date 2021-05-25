package de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling;

import de.blackforestsolutions.dravelopsdatamodel.CallStatus;
import de.blackforestsolutions.dravelopsdatamodel.GraphQlTab;
import org.junit.jupiter.api.Test;

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
}
