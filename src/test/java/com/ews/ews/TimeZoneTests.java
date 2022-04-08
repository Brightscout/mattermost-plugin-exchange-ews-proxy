package com.ews.ews;

import com.brightscout.ews.controller.TimeZoneController;
import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.service.EwsService;
import com.brightscout.ews.service.TimeZoneService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class TimeZoneTests {
    @InjectMocks
    private TimeZoneController timeZoneController;

    @Mock
    private EwsService ewsService;

    @Mock
    private TimeZoneService timeZoneService;

    @Test
    public void getTimeZoneSuccess() {
        String timeZone = "Eastern Standard Time";
        ResponseEntity<String> timeZoneResponse = new ResponseEntity<>(timeZone, HttpStatus.OK);
        Mockito.when(timeZoneService.getTimeZone(ewsService.impersonateUser(TestUtils.EMAIL), TestUtils.EMAIL)).thenReturn(timeZoneResponse);

        ResponseEntity<String> timeZoneResult = timeZoneController.getTimeZone(TestUtils.EMAIL);
        Assertions.assertTrue(timeZoneResult.equals(timeZoneResponse));
    }

    @Test
    public void getTimeZoneFailed() {
        Mockito.when(timeZoneService.getTimeZone(ewsService.impersonateUser(TestUtils.EMAIL), TestUtils.EMAIL)).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> timeZoneController.getTimeZone(TestUtils.EMAIL));
    }
}
