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
import com.example.demo.entity.Income;
import com.example.demo.form.IncomeForm;
import com.example.demo.service.IncomeService;
import com.example.demo.service.LoginService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * 収入管理コントローラー
 * 収入の計算、登録、削除のコントローラーを担う。
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
	
	@GetMapping(UrlConst.INCOME)
	public String showIncomeView(Model model,@AuthenticationPrincipal User user) {
		/** ユーザーの今月の収入額やリストをビューに渡す */
		populateIncomeModel(model,user);
		return "income";
	}
	
	/**
	 * 収入一覧の削除
	 * @param model
	 * @param income
	 * @return 収入管理画面
	 */
	
	@GetMapping(UrlConst.INCOMEDELETE)
	public String deleteIncome(Model model,Income income) {
		incomeservice.deleteIncome(income.getIncome_id());
		return "redirect:/income";
	}
	
	/**
	 * 収入を登録
	 * ビュー側でもエラーチェックはするが開発者ツールで無効にできてしまうのでセキュリティ対策として
	 * コントローラー側でもエラーチェックを施す。
	 * 日付がnull、仕事名の入力なし、金額が0以下の場合エラーメッセージを表示
	 * @param user
	 * @param form
	 * @param model
	 * @return 収入管理画面
	 */
	
	@PostMapping(UrlConst.INCOME)
	public String resistIncome(@AuthenticationPrincipal User user,IncomeForm form,Model model) {
		/** フォームにエラー内容が含まれていた時の例外処理 */
		if (form.getDate() == null || form.getJob()=="" || form.getAmount() <= 0) {
			var errorMsg=AppUtil.getMessage(messageSource,MessageConst.INCOME_WRONG_INPUT);
			/** エラーメッセージとユーザーの収入情報をビューに渡す */
			model.addAttribute("errorMsg",errorMsg);
			populateIncomeModel(model,user);
			return "income";
		}
		else {
			/** ログイン中のユーザー情報を取得し、ユーザー名をフォームにセット */
			var loginuser = loginservice.searchUserByUsername(user.getUsername());
			form.setUsername(loginuser.get().getUsername());
			/** 収入フォームを登録し、一覧に反映させるためにリダイレクト */
			incomeservice.resistIncome(form);
			return "redirect:/income";
		}
		
	}
	
	/**
	 * ログイン中のユーザーの収入の計算後、ビューに収入モデルを渡すメソッド
	 * 支出一覧には今までの収入を掲示するが、収入合計には今月の収入のみ表示
	 * @param model
	 * @param user
	 */
	
	private void populateIncomeModel(Model model,User user) {
		List<Income> incomes = incomeservice.searchIncomeByname(user.getUsername());
		/** 登録されている収入の月が今月と同じ収入のみで計算 */
		int total_income = 0;
		LocalDate today = LocalDate.now();
		for(Income income : incomes) {
			if(income.getDate().getMonth() == today.getMonth()) {
				total_income += income.getAmount();
			}
		}
		model.addAttribute("total_income",total_income);
		model.addAttribute("incomes",incomes);
		model.addAttribute("IncomeForm",new IncomeForm());
	}

}
