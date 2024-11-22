package com.example.demo.service;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Subscription;
import com.example.demo.form.SubscriptionForm;
import com.example.demo.repository.SubscriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
	

	private final JavaMailSender mailSender;
	
	private final SubscriptionRepository subscriptionrepository;
	
	/**
	 * 全てのユーザーのサブスクをリスト型にして返す
	 * @return リスト型のサブスクリプション
	 */
	
	public List<Subscription> findAllUserSubscriptions(){
		return subscriptionrepository.findAll();
	}
	
	/**
	 * メール送信サービス
	 * @param to
	 * @param subject
	 * @param text
	 */
	
	public void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("studysave.app@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
	}
	/**
	 * サブスクリプションリストを返すサービス
	 * @param username
	 * @return List型のサブスクリプション
	 */
	
	public List <Subscription> searchSubscriptionByname(String username){
		return subscriptionrepository.findByUsername(username);	
	}
	
	/**
	 * サブスクリプション登録サービス
	 * @param form
	 */
	
	public void resistSubscription(SubscriptionForm form) {
		var subscription = new Subscription();
		subscription.setUsername(form.getUsername());
		subscription.setAmount(form.getAmount());
		subscription.setNotify(form.getNotify());
		subscription.setSubscription_name(form.getSubscription_name());
		subscription.setBilling_cycle(form.getBilling_cycle());
		subscription.setAnnual_payment_day(form.getAnnual_payment_day());
		subscription.setMonthly_payment_day(form.getMonthly_payment_day());
		subscriptionrepository.save(subscription);
	}
	
	/**
	 * サブスクリプション削除サービス
	 * @param subscription_id
	 */
	
	public void deleteSubscription(Integer subscription_id) {
		subscriptionrepository.deleteById(subscription_id);
	}

}
