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
	
	@GetMapping("/expence")
	public String view(Model model,@AuthenticationPrincipal User user) {
		List<Expence> expences = expenceservice.searchExpenceByname(user.getUsername());
		int total_expence = 0;
		for(Expence expence : expences) {
			total_expence += expence.getAmount();
		}
		model.addAttribute("total_expence",total_expence);
		model.addAttribute("expences",expences);
		model.addAttribute("ExpenceForm",new ExpenceForm());
		return "expence";
	}
	
	@GetMapping("/expence-delete")
	public String deleteExpence(Model model,Expence expence) {
		expenceservice.deleteExpence(expence.getExpence_id());
		return "redirect:/expence";
	}
	
	@PostMapping("/expence")
	public String resistexpence(@AuthenticationPrincipal User user,ExpenceForm form,Model model) {
		if (form.getDate() == null || form.getCategory()=="" || form.getAmount() <= 0) {
			var errorMsg=AppUtil.getMessage(messageSource,MessageConst.INCOME_INPUT_WRONG);
			//もう一度計算しテンプレートに渡す。
			List<Expence> expences = expenceservice.searchExpenceByname(user.getUsername());
			int total_expence = 0;
			for(Expence expence : expences) {
				total_expence += expence.getAmount();
			}
			model.addAttribute("errorMsg",errorMsg); //エラーメッセージをテンプレートに渡す
			model.addAttribute("total_expence",total_expence);
			model.addAttribute("expences",expences);
			model.addAttribute("ExpenceForm",new ExpenceForm());
			return "expence";
		}
		else {
			var loginuser = loginservice.searchUserById(user.getUsername());
			form.setUsername(loginuser.get().getUsername());
			expenceservice.resistExpence(form);
			return "redirect:/expence";
		}
		
	}

}
