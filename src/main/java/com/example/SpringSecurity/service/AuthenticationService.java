package com.example.SpringSecurity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringSecurity.entity.Users;
import com.example.SpringSecurity.repository.UserRepository;
import com.example.SpringSecurity.service.dto.LoginDTO;
import com.example.SpringSecurity.service.dto.RegisterDTO;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	public Users register(RegisterDTO registerDTO) {

		Users user = new Users();
		user.setName(registerDTO.getName());
		user.setEmail(registerDTO.getEmail());
		user.setPassword(encoder.encode(registerDTO.getPassword()));
		user.setRole(registerDTO.getRole());

		return userRepository.save(user);
	}

	public Users login(LoginDTO login) {

	    Optional<Users> optionalUser = userRepository.findByEmail(login.getEmail());

	    if (optionalUser.isEmpty()) {
	        return null; // or throw exception
	    }

	    Users user = optionalUser.get();

	    // First authenticate via Spring Security
	    authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    login.getEmail(),
	                    login.getPassword()
	            )
	    );

	    return user;
	}
}
