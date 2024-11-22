package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.form.FirstLoginForm;
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
	public String showLoginView(Model model,LoginForm form) {
		return "login";
	}
	
	/**
	 * ログインエラー時の画面表示
	 * エラー時 http://localhost:8080/login?errorとなる。
	 * 
	 * @param model
	 * @param form
	 * @return 表示画面
	 */
	
	@GetMapping(value = UrlConst.LOGIN,params="error") 
	public String loginViewError(Model model,LoginForm form) {
		/** Spring Securityでのログイン時のエラー情報をセッション属性として持っている。
		 *  WebAttributes.AUTHENTICATION_EXCEPTIONがエラー情報のキー
		 *  キーの値をExceptionにキャストする
		 */
		var error = (Exception)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		model.addAttribute("errorMsg",error.getMessage());
		return "login";
	}
	
	/**
	 * 初回ログイン時の画面表示
	 * 現在の貯金と目標を記入
	 * 
	 * @param model
	 * @param form
	 * @param User
	 * @return 初回ログイン画面
	 */
	
	@GetMapping(UrlConst.FIRSTLOGIN)
	public String showFirstLoginview(Model model,@ModelAttribute("FirstLoginForm") FirstLoginForm form) {
		return "first-login";
	}
	
	/**
	 * ログイン時の処理
	 * フォームで送られてきたユーザー名がDB上に存在し、
	 * パスワードがDB上のパスワード(暗号化されている)と一致するか確認
	 * @param model
	 * @param form
	 * @return メニュー画面
	 */
	
	@PostMapping(UrlConst.LOGIN)
	public String checkLogin(Model model,LoginForm form) {
		var user = service.searchUserByUsername(form.getUsername());
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
	
	/**
	 * 初回ログイン時のフォームの処理
	 * 現在の貯金額よりも目標が高いか確認。
	 * 問題がなければDB上に保存
	 * @param model
	 * @param form
	 * @param user
	 * @return メニュー画面
	 */
	
	@PostMapping(UrlConst.FIRSTLOGIN)
	public String checkFirstLogin(Model model,@ModelAttribute("FirstLoginForm") FirstLoginForm form,@AuthenticationPrincipal User user) {
		if(form.getSavings() >= form.getGoal() ) {
			model.addAttribute("errorMsg","目標よりも現在の貯金額の方が高いです");
			return "first-login";
		}
		service.resistFirstInfo(form,user.getUsername());
		return "redirect:/menu";
	}
}
