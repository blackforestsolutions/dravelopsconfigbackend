package de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice;

import de.blackforestsolutions.dravelopsconfigbackend.exceptionhandling.RefreshExecutionException;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallService;
import de.blackforestsolutions.dravelopsconfigbackend.service.communicationservice.restcalls.CallServiceImpl;
import de.blackforestsolutions.dravelopsdatamodel.ApiToken;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static de.blackforestsolutions.dravelopsconfigbackend.objectmothers.ApiTokenObjectMother.getConfigBackendConfigurationApiToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RefreshConfigServiceTest {

    private final ApiToken configBackendConfigurationApiToken = getConfigBackendConfigurationApiToken();
    private final CallService callService = mock(CallServiceImpl.class);

    private final RefreshConfigService classUnderTest = new RefreshConfigServiceImpl(callService, configBackendConfigurationApiToken);

    @Test
    void test_refreshConfigs_is_executed_correctly() {
        when(callService.put(anyString(), any(HttpEntity.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        ArgumentCaptor<String> urlArg = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpEntity> httpEntityArg = ArgumentCaptor.forClass(HttpEntity.class);

        classUnderTest.refreshConfigs();

        verify(callService, times(1)).put(urlArg.capture(), httpEntityArg.capture());
        assertThat(urlArg.getValue()).isEqualTo("http://localhost:8092/actuator/bus-refresh");
        assertThat(httpEntityArg.getValue()).isEqualTo(HttpEntity.EMPTY);
    }

    @Test
    void test_refreshConfigs_throws_exception_when_httpStatus_is_not_correct() {
        when(callService.put(anyString(), any(HttpEntity.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        assertThrows(RefreshExecutionException.class, classUnderTest::refreshConfigs);
    }


}
