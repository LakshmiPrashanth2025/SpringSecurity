package com.example.SpringSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringSecurity.entity.Users;
import com.example.SpringSecurity.service.AuthenticationService;
import com.example.SpringSecurity.service.JWTAuthorizationService;
import com.example.SpringSecurity.service.UserService;
import com.example.SpringSecurity.service.dto.LoginDTO;
import com.example.SpringSecurity.service.dto.RegisterDTO;

@RestController
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private JWTAuthorizationService jwtService;

	
	@PostMapping("/register")
	public Users register(@RequestBody RegisterDTO register) {
		return authService.register(register);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginDTO login) {
		Users user = authService.login(login);
		if (user != null) {
			return jwtService.generateToken(user);
		} else {
			return "Invalid credentials";
		}
	}	
	

}
