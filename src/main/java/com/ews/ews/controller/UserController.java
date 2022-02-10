package com.ews.ews.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import com.ews.ews.service.EWSService;
import com.ews.ews.service.UserService;

import com.ews.ews.model.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private EWSService ewsService;

	private UserService userService;

	public UserController(EWSService ewsService, UserService userService) {
		this.ewsService = ewsService;
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<User> getUser(@RequestParam String email) throws Exception {
		return userService.getUser(ewsService.impersonateUser(email), email);
	}
}
