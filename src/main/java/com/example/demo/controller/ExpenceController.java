package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

/**
 * 支出管理コントローラー
 */

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
	public String view(Model model,@AuthenticationPrincipal User user) {
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
	 * @param user
	 * @param form
	 * @param model
	 * @return 支出管理画面
	 */
	
	@PostMapping(UrlConst.EXPENCE)
	public String resistexpence(@AuthenticationPrincipal User user,ExpenceForm form,Model model) {
		if (form.getDate() == null || form.getCategory()=="" || form.getAmount() <= 0) {
			var errorMsg=AppUtil.getMessage(messageSource,MessageConst.INCOME_INPUT_WRONG);
			model.addAttribute("errorMsg",errorMsg);
			populateExpenceModel(model,user);
			return "expence";
		}
		else {
			var loginuser = loginservice.searchUserById(user.getUsername());
			form.setUsername(loginuser.get().getUsername());
			expenceservice.resistExpence(form);
			return "redirect:/expence";
		}
		
	}
	
	/**
	 * 支出の計算後、ビューに情報を渡すメソッド
	 * @param model
	 * @param user
	 */
	
	private void populateExpenceModel(Model model,User user) {
		LocalDate today = LocalDate.now();
		List<Expence> expences = expenceservice.searchExpenceByname(user.getUsername());
		int total_expence = 0;
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
