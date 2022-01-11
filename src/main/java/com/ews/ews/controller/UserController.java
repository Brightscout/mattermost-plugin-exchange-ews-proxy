package com.ews.ews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import com.ews.ews.service.EWSService;
import com.ews.ews.service.UserService;

import com.ews.ews.model.User;

@RestController
@RequestMapping("/api/me")
public class UserController {
    @Autowired
	EWSService ewsService;
    
    @Autowired
	UserService userService;
    	
	@GetMapping
	public ResponseEntity<User> getUser(@RequestParam String email) throws Exception {
		return this.userService.getUser(this.ewsService.impersonateUser(email), email);
	}
}
