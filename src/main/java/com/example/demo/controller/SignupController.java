package com.example.demo.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.form.SignupForm;
import com.example.demo.service.SignupService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー登録画面 Controller
 * 
 * @author syuto
 */

@Controller 
@RequiredArgsConstructor
public class SignupController {
	
	/** ユーザー登録画面 service */
	private final SignupService service;
	
	/**メッセージソース*/
	private final MessageSource messageSource;
	
	
	@GetMapping(UrlConst.SIGNUP)
	public String view(SignupForm form) {
		return "signup";
	}
	
	@PostMapping(UrlConst.SIGNUP)
	public void signup(Model model, SignupForm form) {
		var user = service.resistUser(form); //userはoptional型
		//ユーザーが存在した場合
		if(user.isEmpty()) {
			var errorMsg=AppUtil.getMessage(messageSource, MessageConst.SIGNUP_DEPLICATE_LOGIN_ID);
			model.addAttribute("Message",errorMsg);
		}
		//ユーザーが存在しなかった場合
		else {
			var succeedMsg=AppUtil.getMessage(messageSource, MessageConst.SIGNUP_SUCCEED);
			model.addAttribute("Message",succeedMsg);
		}
	}

}
