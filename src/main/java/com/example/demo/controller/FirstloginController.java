package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FirstloginController {
	
	/** ログイン画面 service */
	private final LoginService service;
	
	@GetMapping("first-login")
	public String view(Model model,@ModelAttribute("LoginForm") LoginForm form) {
//		model.addAttribute("loginForm", new LoginForm());
		return "first-login";
	}
	
	@PostMapping("first-login")
	public String firstlogin(Model model,LoginForm form,@AuthenticationPrincipal User user) {
		service.resistSavings(form,user.getUsername());
		
		return "menu";
	}

}
