package com.ews.ews;

import com.brightscout.ews.controller.UserController;
import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.User;
import com.brightscout.ews.service.EwsService;
import com.brightscout.ews.service.UserService;
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
public class UserTests {
    @InjectMocks
    private UserController userController;

    @Mock
    private EwsService ewsService;

    @Mock
    private UserService userService;

    @Test
    public void getUserSuccess() {
        User user = new User(TestUtils.ID, "test-user", TestUtils.EMAIL);
        ResponseEntity<User> userResponse = new ResponseEntity<>(user, HttpStatus.OK);
        Mockito.when(userService.getUser(ewsService.impersonateUser(TestUtils.EMAIL), TestUtils.EMAIL)).thenReturn(userResponse);

        ResponseEntity<User> userResult = userController.getUser(TestUtils.EMAIL);
        Assertions.assertTrue(userResult.equals(userResponse));

    }

    @Test
    public void getUserFailed() {
        Mockito.when(userService.getUser(ewsService.impersonateUser(TestUtils.EMAIL), TestUtils.EMAIL)).thenThrow(InternalServerException.class);
        Assertions.assertThrows(InternalServerException.class, () -> userController.getUser(TestUtils.EMAIL));
    }
}
