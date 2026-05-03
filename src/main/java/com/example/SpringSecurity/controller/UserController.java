package com.example.SpringSecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringSecurity.entity.Users;
import com.example.SpringSecurity.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello world";
	}
	
//	@GetMapping("/register")
//	public String register() {
//		return "Registration page";
//	}
	
	//Role based authorization
	@GetMapping("/home")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String home() {
		return "Welcome to the Home Page!";
	}
	
	@GetMapping("/users")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Users> getAllUsers() {
		return userService.getAllUsers();
	}

}
