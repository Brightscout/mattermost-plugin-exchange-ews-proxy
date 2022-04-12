package com.brightscout.ews.controller;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brightscout.ews.exception.InternalServerException;
import com.brightscout.ews.model.User;
import com.brightscout.ews.service.EwsService;
import com.brightscout.ews.service.UserService;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

	private EwsService ewsService;

	private UserService userService;

	@Autowired
	public UserController(EwsService ewsService, UserService userService) {
		this.ewsService = ewsService;
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<User> getUser(@RequestParam @Email String email) throws InternalServerException {
		return userService.getUser(ewsService.impersonateUser(email), email);
	}
}
