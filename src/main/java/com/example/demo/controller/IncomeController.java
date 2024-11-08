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
import com.example.demo.entity.Income;
import com.example.demo.form.IncomeForm;
import com.example.demo.service.IncomeService;
import com.example.demo.service.LoginService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * 収入管理コントローラー
 */

@Controller
@RequiredArgsConstructor
public class IncomeController {
	/** 収入画面サービス*/
	private final IncomeService incomeservice;  
	
	/** ログイン画面サービス*/
	private final LoginService loginservice;
	
	/**メッセージソース*/
	private final MessageSource messageSource;
	
	/**
	 * 	収入管理画面表示
	 * @param model
	 * @param user
	 * @return 収入管理画面
	 */
	
	@GetMapping("/income")
	public String view(Model model,@AuthenticationPrincipal User user) {
		populateIncomeModel(model,user);
		return "income";
	}
	
	/**
	 * 収入一覧の削除
	 * @param model
	 * @param income
	 * @return 収入管理画面
	 */
	
	@GetMapping("/income-delete")
	public String deleteIncome(Model model,Income income) {
		incomeservice.deleteIncome(income.getIncome_id());
		return "redirect:/income";
	}
	
	/**
	 * 収入を登録
	 * @param user
	 * @param form
	 * @param model
	 * @return 収入管理画面
	 */
	
	@PostMapping("/income")
	public String resistincome(@AuthenticationPrincipal User user,IncomeForm form,Model model) {
		if (form.getDate() == null || form.getJob()=="" || form.getAmount() <= 0) {
			var errorMsg=AppUtil.getMessage(messageSource,MessageConst.INCOME_INPUT_WRONG);
			model.addAttribute("errorMsg",errorMsg);
			populateIncomeModel(model,user);
			return "income";
		}
		else {
			var loginuser = loginservice.searchUserById(user.getUsername());
			form.setUsername(loginuser.get().getUsername());
			incomeservice.resistIncome(form);
			return "redirect:/income";
		}
		
	}
	
	/**
	 * 収入の計算後、ビューに情報を渡すメソッド
	 * @param model
	 * @param user
	 */
	
	private void populateIncomeModel(Model model,User user) {
		List<Income> incomes = incomeservice.searchIncomeByname(user.getUsername());
		int total_income = 0;
		for(Income income : incomes) {
			total_income += income.getAmount();
		}
		model.addAttribute("total_income",total_income);
		model.addAttribute("incomes",incomes);
		model.addAttribute("IncomeForm",new IncomeForm());
	}

}
