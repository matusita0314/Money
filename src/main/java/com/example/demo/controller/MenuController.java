package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.constant.UrlConst;
import com.example.demo.service.ExpenceService;
import com.example.demo.service.IncomeService;
import com.example.demo.service.LoginService;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;

/**
 * メニューコントローラー
 * 
 * @author syuto
 */

@Controller
@RequiredArgsConstructor
public class MenuController {
	/** 収入画面サービス*/
	private final IncomeService incomeservice;
	
	/** 支出画面サービス*/
	private final ExpenceService expenceservice;
	
	/** ユーザーサービス*/
	private final LoginService loginservice;
	
	/**
	 * ログイン時に初回ログインかどうか判定し、現在の貯金額を入力
	 * 貯金額と収入と支出額を加算
	 *   
	 * @param model
	 * @param user
	 * @return
	 */
	
	@GetMapping(UrlConst.MENU)
	public String view(Model model,@AuthenticationPrincipal User user) {
		var loginuser = loginservice.searchUserById(user.getUsername());
		var isFirsttimeLogin = loginuser.get().getFirstLogin();
		if(isFirsttimeLogin) {
			loginuser.get().setFirstLogin(false);
			return "first-login";
		}
//		List<Income> incomes = incomeservice.searchIncomeByname(user.getUsername());
//		List<Expence> expences = expenceservice.searchExpenceByname(user.getUsername());
//		int total_money = 0;
//		for(Expence expence : expences) total_money -= expence.getAmount();
//		for(Income income : incomes) total_money += income.getAmount();
		
		return "menu";
	}
	
}
