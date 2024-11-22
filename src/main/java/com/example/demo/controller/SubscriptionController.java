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
import com.example.demo.entity.Subscription;
import com.example.demo.form.SubscriptionForm;
import com.example.demo.service.LoginService;
import com.example.demo.service.SubscriptionService;
import com.example.demo.util.AppUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SubscriptionController {
	
	/** サブスクリプションサービス */
	private final SubscriptionService subscriptionservice;

	/** ログインサービス */
	private final LoginService loginservice;
	
	/** メッセージソース */
	private final MessageSource messagesource;
	
	/**
	 * サブスクリプション管理画面表示
	 * @param model
	 * @param user
	 * @return サブスクリプション管理画面
	 */
	
	@GetMapping(UrlConst.SUBSCRIPTION)
	public String showSubscriptionView(Model model,@AuthenticationPrincipal User user) {
		List <Subscription> subscriptions = subscriptionservice.searchSubscriptionByname(user.getUsername());
		LocalDate today = LocalDate.now();
		model.addAttribute("endOfMonth",today.lengthOfMonth());
		model.addAttribute("subscriptions",subscriptions);
		model.addAttribute("subscriptionForm",new SubscriptionForm());
		return "subscription";
	}
	
	/**
	 * サブスクリプション登録
	 * @param user
	 * @param form
	 * @param model
	 * @return サブスクリプション管理画面
	 */
	
	@PostMapping(UrlConst.SUBSCRIPTION)
	public String resistSubscription(@AuthenticationPrincipal User user,SubscriptionForm form,Model model) {
		/** サブスクフォームに誤りがあった際の例外処理 */
		if(judgeError(form)) {
			var errorMsg = AppUtil.getMessage(messagesource,MessageConst.SUBSCRIPTION_INPUT_WRONG);
			List <Subscription> subscriptions = subscriptionservice.searchSubscriptionByname(user.getUsername());
			model.addAttribute("errorMsg",errorMsg);
			model.addAttribute("subscriptions",subscriptions);
			model.addAttribute("SubscriptionForm",new SubscriptionForm());
			return "subscription";
		}
		else {
			var loginuser = loginservice.searchUserByUsername(user.getUsername());
			form.setUsername(loginuser.get().getUsername());
			subscriptionservice.resistSubscription(form);
			return "redirect:/subscription";
		}
	}
	
	/**
	 * サブスクリプション削除
	 * @param subscription
	 * @return サブスクリプション管理画面
	 */
	
	@GetMapping(UrlConst.SUBSCRIPTIONDELETE)
	public String deleteSubscription(Subscription subscription) {
		subscriptionservice.deleteSubscription(subscription.getSubscription_id());
		return "redirect:/subscription";
	}
	
	/**
	 * フォームの誤りチェックメソッド
	 * 月間払いの時、「選択してください」が送信されると100が値に入るので、エラーメッセージを渡す
	 * @param form
	 * @return boolean
	 */
	
	private boolean judgeError(SubscriptionForm form) {
		if(form.getBilling_cycle().equals("MONTHLY")) {
			if(form.getMonthly_payment_day() == 100 || form.getAmount() == 0) {
				return true;
			}
		}
		else {
			if(form.getAnnual_payment_day() == null) {
				return true;
			}
		}
		return false;
	}

}
