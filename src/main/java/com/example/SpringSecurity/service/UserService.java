package com.example.SpringSecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SpringSecurity.entity.Users;
import com.example.SpringSecurity.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<Users> getAllUsers(){
		return userRepository.findAll();
	}
	public Users getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
			
}
