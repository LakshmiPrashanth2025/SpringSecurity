package com.example.SpringSecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringSecurity.entity.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
	
	//Derived Query
	
	//select * from Users where email='email'
	 Optional<Users> findByEmail(String email);
	
	

}
