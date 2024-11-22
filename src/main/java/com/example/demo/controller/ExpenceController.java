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
import com.example.demo.form.ExpenceForm;
import com.example.demo.service.ExpenceService;
import com.example.demo.service.LoginService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

/**
 * 支出管理コントローラー
 * 支出の計算、登録、削除のコントローラーを担う。
 */

@Controller
@RequiredArgsConstructor
public class ExpenceController {
	/** 支出画面サービス*/
	private final ExpenceService expenceservice;  
	
	/** ログイン画面サービス*/
	private final LoginService loginservice;
	
	/**メッセージソース*/
	private final MessageSource messageSource;
	
	/**
	 * 	支出管理画面表示
	 * @param model
	 * @param user
	 * @return 支出管理画面
	 */
	
	@GetMapping(UrlConst.EXPENCE)
	public String showExpenceView(Model model,@AuthenticationPrincipal User user) {
		/** ユーザーの今月の支出額やリストをビューに渡す */ 
		populateExpenceModel(model,user);
		return "expence";
	}
	
	/**
	 * 支出一覧の削除
	 * @param model
	 * @param expence
	 * @return 支出管理画面
	 */
	
	@GetMapping(UrlConst.EXPENCEDELETE)
	public String deleteExpence(Expence expence) {
		expenceservice.deleteExpence(expence.getExpence_id());
		return "redirect:/expence";
	}
	
	/**
	 * 支出を登録
	 * ビュー側でもエラーチェックはするが開発者ツールで無効にできてしまうのでセキュリティ対策として
	 * コントローラー側でもエラーチェックを施す。
	 * 日付がnull、カテゴリーの入力なし、金額が0以下の場合エラーメッセージ
	 * @param user
	 * @param form
	 * @param model
	 * @return 支出管理画面
	 */
	
	@PostMapping(UrlConst.EXPENCE)
	public String resistExpence(@AuthenticationPrincipal User user,ExpenceForm form,Model model) {
		/** フォームにエラー内容が含まれていた時の例外処理 */
		if (form.getDate() == null || form.getCategory()=="" || form.getAmount() <= 0) {
			var errorMsg=AppUtil.getMessage(messageSource,MessageConst.EXPENCE_WRONG_INPUT);
			/** エラーメッセージとユーザーの支出情報をビューに渡す */
			model.addAttribute("errorMsg",errorMsg);
			populateExpenceModel(model,user);
			return "expence";
		}
		else {
			/** ログイン中のユーザー情報を取得し、ユーザー名をフォームにセット */ 
			var loginuser = loginservice.searchUserByUsername(user.getUsername());
			form.setUsername(loginuser.get().getUsername());
			/** 支出フォームを登録し、一覧に反映させるためにリダイレクト */ 
			expenceservice.resistExpence(form);
			return "redirect:/expence";
		}
		
	}
	
	/**
	 * ログイン中のユーザーの支出の計算後、ビューに支出モデルを渡すメソッド
	 * 支出一覧には今までの支出を掲示するが、支出合計には今月の支出のみ表示
	 * @param model
	 * @param user
	 */
	
	private void populateExpenceModel(Model model,User user) {
		List<Expence> expences = expenceservice.searchExpenceByname(user.getUsername());
		/** 登録されている支出の月が今月と同じ支出のみで計算 */
		int total_expence = 0;
		LocalDate today = LocalDate.now();
		for(Expence expence : expences) {
			if(expence.getDate().getMonth() == today.getMonth()) {
				total_expence += expence.getAmount();
			}
		}
		model.addAttribute("total_expence",total_expence);
		model.addAttribute("expences",expences);
		model.addAttribute("ExpenceForm",new ExpenceForm());
	}

}
