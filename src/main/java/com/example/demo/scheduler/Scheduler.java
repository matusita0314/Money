package com.example.demo.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Subscription;
import com.example.demo.form.ExpenceForm;
import com.example.demo.service.ExpenceService;
import com.example.demo.service.LoginService;
import com.example.demo.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Scheduler {

	/** サブスクリプションサービス */
	private final SubscriptionService subscriptionservice;

	/** ユーザー情報取得サービス */
	private final LoginService loginservice;

	/** 支出管理サービス */
	private final ExpenceService expenceservice;

	/**
	 * 毎日AM9:00に全てのユーザーのサブスクリプションの支払日の前日ではないか確認
	 * 前日の場合、メールを送信
	 */

	@Scheduled(cron = "0 0 9 * * ?")

	public void checkandsendEmail() {
		List<Subscription> subscriptions = subscriptionservice.findAllSubscriptions();
		LocalDate today = LocalDate.now();

		for (Subscription subscription : subscriptions) {
			/** 通知オフならここでストップ */
			if (subscription.getNotify() == false) {
				continue;
			}
			/** ユーザー情報を取得 */
			String username = subscription.getUsername();
			var user = loginservice.searchUserById(username);
			String userMailaddress = user.get().getMailAddress();

			/** 月間払い */
			if (subscription.getBilling_cycle().equals("MONTHLY")) {
				/** 支払日が月の初日の場合、先月の末日にメールを送信 */
				if (subscription.getMonthly_payment_day() == 1) {
					if (today.getDayOfMonth() == today.lengthOfMonth()) {
						subscriptionservice.sendEmail(userMailaddress, "サブスクの支払日について",
								 username + "様\n\n" + "StudiSaveをご利用いただきありがとうございます。\n" + "こちらはサブスクリプションの支払い日の前日となりましたことを報告するメールとなっております。\n\n" + subscription.getSubscription_name() + "の支払日の前日になりましたことを報告いたします。");
					}
				} else {
					if (today.getDayOfMonth() == subscription.getMonthly_payment_day() - 1) {
						subscriptionservice.sendEmail(userMailaddress, "サブスクの支払日について",
								 username + "様\n\n" + "StudiSaveをご利用いただきありがとうございます。\n" + "こちらはサブスクリプションの支払い日の前日となりましたことを報告するメールとなっております。\n\n" + subscription.getSubscription_name() + "の支払日の前日になりましたことを報告いたします。");
					}
				}
			}

			/** 年間払い */
			if (subscription.getBilling_cycle().equals("ANNUAL")) {
				/** 支払日が月の初日の場合、先月の末日にメールを送信 */
				if (subscription.getAnnual_payment_day().getDayOfMonth() == 1) {
					if (today.getDayOfMonth() == today.lengthOfMonth()) {
						subscriptionservice.sendEmail(userMailaddress, "サブスクの支払日について",
								 username + "様\n\n" + "StudiSaveをご利用いただきありがとうございます。\n" + "こちらはサブスクリプションの支払い日の前日となりましたことを報告するメールとなっております。\n\n" + subscription.getSubscription_name() + "の支払日の前日になりましたことを報告いたします。");
					}
				} else {
					if (today.getDayOfMonth() == subscription.getAnnual_payment_day().getDayOfMonth() - 1) {
						subscriptionservice.sendEmail(userMailaddress, "サブスクの支払日について",
								 username + "様\n\n" + "StudiSaveをご利用いただきありがとうございます。\n" + "こちらはサブスクリプションの支払い日の前日となりましたことを報告するメールとなっております。\n\n" + subscription.getSubscription_name() + "の支払日の前日になりましたことを報告いたします。");
					}
				}
			}
		}
	}

	/**
	 * 日付が変わると同時にサブスクの支払日か確認
	 * 支払日の場合、支出に加算
	 */

	@Scheduled(cron = "0 0 0 * * ?")
	public void checkandExpence() {
		List <Subscription> subscriptions = subscriptionservice.findAllSubscriptions();
		LocalDate today = LocalDate.now();
		
		for (Subscription subscription : subscriptions) {
			String username = subscription.getUsername();
			if(subscription.getBilling_cycle().equals("MONTHLY")) {
				if(subscription.getMonthly_payment_day() == today.getDayOfMonth()) {
					ExpenceForm form = new ExpenceForm();
					form.setAmount(subscription.getAmount());
					form.setCategory(subscription.getSubscription_name());
					form.setDate(today);
					form.setUsername(username);
					expenceservice.resistExpence(form);
				}
			}
			else {
				if(subscription.getAnnual_payment_day().getDayOfMonth() == today.getDayOfMonth()) {
					ExpenceForm form = new ExpenceForm();
					form.setAmount(subscription.getAmount());
					form.setCategory(subscription.getSubscription_name());
					form.setDate(today);
					form.setUsername(username);
					expenceservice.resistExpence(form);
				}
			}	
		}	
	}

}
