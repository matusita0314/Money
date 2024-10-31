package com.example.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;

import lombok.RequiredArgsConstructor;

/**
 * ログイン画面 Controller
 * 
 * @author syuto
 */

@Controller 
@RequiredArgsConstructor
public class LoginController {
	
	/** ログイン画面 service */
	private final LoginService service;
	
	/** PasswordEncoder passwordEncoderにBCryptPasswordEncoder()が入ってくる*/
	private final PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/login") 
	public String view(Model model,LoginForm form) {
		
		return "login";
		
	}
	
	@PostMapping("/login")
	public String login(Model model,LoginForm form) {
		var user = service.searchUserById(form.getLoginId());
		var isCorrectUserAuth = user.isPresent() 
				&& form.getPassword().equals(user.get().getPassword());
		if(isCorrectUserAuth) {
			return "redirect:/menu";
		 }
		else {
			model.addAttribute("error","ログインIDとパスワードのどちらかが違います。");
			return "login";
		}
	}
}
