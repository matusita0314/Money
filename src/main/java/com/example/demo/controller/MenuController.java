package com.example.demo.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.entity.Expence;
import com.example.demo.entity.Income;
import com.example.demo.form.ChangeGoalForm;
import com.example.demo.service.ExpenceService;
import com.example.demo.service.IncomeService;
import com.example.demo.service.LoginService;
import com.example.demo.util.AppUtil;

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
	
	/**メッセージソース*/
	private final MessageSource messageSource;
	
	/**
	 * ログイン時に初回ログインかどうか判定し、現在の貯金額と目標を入力
	 * 貯金額と収入と支出額を加算
	 *   
	 * @param model
	 * @param user
	 * @return first-login / menu
	 */
	
	@GetMapping(UrlConst.MENU)
	public String view(Model model,@AuthenticationPrincipal User user) {
		var loginuser = loginservice.searchUserById(user.getUsername());
		var isFirsttimeLogin = loginuser.get().getFirstLogin();
		if(isFirsttimeLogin) {
		/** 初回ログインのフラグを戻す */
			loginservice.resistFirstlogin(user.getUsername()); 
			return "redirect:/first-login";
		}
		/** ユーザーの現在の所持金を計算 */
		List<Income> incomes = incomeservice.searchIncomeByname(user.getUsername());
		List<Expence> expences = expenceservice.searchExpenceByname(user.getUsername());
		int total_money = 0;
		int total_income = 0;
        int total_expence = 0;
		for(Income income : incomes) total_income += income.getAmount();
		for(Expence expence : expences) total_expence += expence.getAmount();
		total_money = total_income - total_expence + loginuser.get().getSavings();
		
		/**目標までの金額を計算 */
		int remain = loginuser.get().getGoal() - total_money;
		
		model.addAttribute("remain",remain);
		model.addAttribute("income",total_income);
		model.addAttribute("expence",total_expence);
		model.addAttribute("goal",loginuser.get().getGoal());
		model.addAttribute("total_money",total_money);
		return "menu";
	}
	
	/**
	 * 
	 * @param form
	 * @return 目標変更画面
	 */
	
	@GetMapping("/change-goal")
	public String changeview(ChangeGoalForm form) {
		return "change-goal";
	}
	
	/**
	 * 目標を変更するためのメソッド
	 * 
	 * @param model
	 * @param form
	 * @param User
	 * @return メニュー画面
	 */
	
	@PostMapping("/change-goal")
	public String change(Model model,ChangeGoalForm form,@AuthenticationPrincipal User user) {
		var loginuser = loginservice.searchUserById(user.getUsername());
		if(form.getGoal() <= loginuser.get().getSavings()) {
			var errorMsg=AppUtil.getMessage(messageSource,MessageConst.GOAL_INPUT_WRONG);
			model.addAttribute("errorMsg",errorMsg);
			return "change-goal";
		}
		loginservice.resistGoal(form,user.getUsername());
		return "redirect:/menu";
	}
	
}
