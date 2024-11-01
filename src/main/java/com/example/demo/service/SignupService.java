package com.example.demo.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー登録 service
 */

@Service
@RequiredArgsConstructor
public class SignupService {
	
	/** ユーザー登録情報DAO */
	private final UserRepository repository;
	
	private final PasswordEncoder passwordEncoder;
	
	/**
	 * ユーザー情報テーブルの新規登録
	 * 入力されたパスワードを暗号化して登録する。
	 * 
	 * @param form 入力情報
	 * @return Optional(User) か Optional.empty
	 */
	
	public Optional<User> resistUser(SignupForm form){
		var checkDeplicateUser = repository.findById(form.getUsername());
		if(checkDeplicateUser.isPresent()) {
			return Optional.empty(); 
		}
		var user = new User();
		user.setMailAddress(form.getMail_address());
		user.setPassword(form.getPassword());
		user.setUsername(form.getUsername());
		
		var encodedPassword = passwordEncoder.encode(form.getPassword());
		user.setPassword(encodedPassword);
		return Optional.of(repository.save(user)); //trueが返る
	}
}
