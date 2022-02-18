package com.ews.ews.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ews.ews.model.User;
import com.ews.ews.service.EwsService;
import com.ews.ews.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private EwsService ewsService;

	private UserService userService;

	public UserController(EwsService ewsService, UserService userService) {
		this.ewsService = ewsService;
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<User> getUser(@RequestParam String email) throws Exception {
		return userService.getUser(ewsService.impersonateUser(email), email);
	}
}
