package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
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
	
	/**セッション情報*/
	private final HttpSession session;
	
	/**
	 * 初期表示
	 * 
	 * @param model
	 * @param form
	 * @return 表示画面
	 */
	
	@GetMapping(UrlConst.LOGIN) 
	public String view(Model model,LoginForm form) {
		return "login";
	}
	
	/**
	 *　ログインエラー時の画面表示
	 *  エラー時 http://localhost:8080/login?errorとなる。
	 * 
	 * @param model
	 * @param form
	 * @return 表示画面
	 */
	
	@GetMapping(value = UrlConst.LOGIN,params="error") 
	public String viewError(Model model,LoginForm form) {
		var error = (Exception)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		model.addAttribute("errorMsg",error.getMessage());
		return "login";
	}
	
	/**
	 * ログイン
	 * 
	 * @param model
	 * @param form
	 * @return 表示画面
	 */
	
	@PostMapping(UrlConst.LOGIN)
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
