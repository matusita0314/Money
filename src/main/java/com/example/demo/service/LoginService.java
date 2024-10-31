package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
	
	private final UserRepository repository;
	
	public Optional<User> searchUserById(Integer user_id){
		return repository.findById(user_id);
	}
	
}
