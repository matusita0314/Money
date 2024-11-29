package com.example.demo.controller;

import java.time.LocalDate;
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
	 * @return メニュー画面
	 */
	
	@GetMapping(UrlConst.MENU)
	public String showMenuView(Model model,@AuthenticationPrincipal User user) {
		var loginuser = loginservice.searchUserByUsername(user.getUsername());
		var isFirsttimeLogin = loginuser.get().getFirstLogin();
		
		/** 初回ログインのフラグを戻し、firstlogin画面に飛ばす */
		if(isFirsttimeLogin) {
			loginservice.resistFirstlogin(user.getUsername()); 
			return "redirect:/first-login";
		}
		
		/** ユーザーの現在の所持金を計算。今月の支出はこの中に含めるが今月の収入は振り込まれていないため含めない。*/
		List<Income> incomes = incomeservice.searchIncomeByname(user.getUsername());
		List<Expence> expences = expenceservice.searchExpenceByname(user.getUsername());
		int total_money = 0;
        int total_expence = 0;
		for(Expence expence : expences) total_expence += expence.getAmount();
<<<<<<< HEAD
		total_money =loginuser.get().getSavings() - total_expence;
=======
		total_money =total_expence + loginuser.get().getSavings();
>>>>>>> fd71f6396410125131557066113d3c4e43805c16
		
		/**目標までの金額を計算 */
		int remain = loginuser.get().getGoal() - total_money;
		
		/** 収入と支出のグラフ用のデータ */
		List<Integer> monthlyIncomes = incomeservice.getMonthlyIncome(user.getUsername());
	    List<Integer> monthlyExpences = expenceservice.getMonthlyExpence(user.getUsername());
	    
	    /** 今月の収入と支出の計算 */
	    int thismonth_income = 0;
		LocalDate today = LocalDate.now();
		for(Income income : incomes) {
			if(income.getDate().getMonth() == today.getMonth()) {
				thismonth_income += income.getAmount();
			}
		}
		int thismonth_expence = 0;
		for(Expence expence : expences) {
			if(expence.getDate().getMonth() == today.getMonth()) {
				thismonth_expence += expence.getAmount();
			}
		}
		
		model.addAttribute("monthlyExpences", monthlyExpences);
		model.addAttribute("monthlyIncomes", monthlyIncomes);
		model.addAttribute("thismonth_income",thismonth_income);
		model.addAttribute("thismonth_expence",thismonth_expence);
		model.addAttribute("remain",remain);
		model.addAttribute("goal",loginuser.get().getGoal());
		model.addAttribute("total_money",total_money);
		model.addAttribute("incomes",incomes);
		model.addAttribute("expences",expences);
		return "menu";
	}
	
	/**
	 * 目標を変更するためのGETメソッド
	 * @param form
	 * @return 目標変更画面
	 */
	
	@GetMapping(UrlConst.CHANGEGOAL)
	public String changeview(ChangeGoalForm form) {
		return "change-goal";
	}
	
	/**
	 * 目標を変更するためのPOSTメソッド
	 * 
	 * @param model
	 * @param form
	 * @param User
	 * @return メニュー画面
	 */
	
	@PostMapping(UrlConst.CHANGEGOAL)
	public String change(Model model,ChangeGoalForm form,@AuthenticationPrincipal User user) {
		var loginuser = loginservice.searchUserByUsername(user.getUsername());
		/** 目標よりも貯金額の方が多い時の例外処理 */
		if(form.getGoal() <= loginuser.get().getSavings()) {
			var errorMsg=AppUtil.getMessage(messageSource,MessageConst.GOAL_INPUT_WRONG);
			model.addAttribute("errorMsg",errorMsg);
			return "change-goal";
		}
		loginservice.resistGoal(form,user.getUsername());
		return "redirect:/menu";
	}
	
}
