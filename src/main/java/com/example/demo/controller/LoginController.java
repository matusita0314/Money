package com.example.demo.controller;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;
import com.example.demo.util.AppUtil;

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
	
	/** PasswordEncoder passwordEncoderにBCryptPasswordEncoder()を入れる*/
	private final PasswordEncoder passwordEncoder;
	
	/**メッセージソース*/
	private final MessageSource messageSource;
	
	/**
	 * 初期表示
	 * 
	 * @param model
	 * @param form
	 * @return 表示画面
	 */
	
	@GetMapping("/login") 
	public String view(Model model,LoginForm form) {
		return "login";
	}
	
	/**
	 * ログイン
	 * 
	 * @param model
	 * @param form
	 * @return 表示画面
	 */
	
	@PostMapping("/login")
	public String login(Model model,LoginForm form) {
		var user = service.searchUserById(form.getUsername());
		var isCorrectUserAuth = user.isPresent()
				&& passwordEncoder.matches(form.getPassword(), user.get().getPassword());
		if(isCorrectUserAuth) {
			return "redirect:/menu";
		 }
		else {
			var errorMsg=AppUtil.getMessage(messageSource, MessageConst.LOGIN_WRONG_INPUT);
			model.addAttribute("errorMsg",errorMsg);
			return "login";
		}
	}
}
