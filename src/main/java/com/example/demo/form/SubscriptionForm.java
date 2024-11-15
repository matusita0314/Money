package com.example.demo.form;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SubscriptionForm {
	
	private int amount;
	private Boolean notify;
	private String subscription_name;
	private String username;
	private String billing_cycle;
	private int monthly_payment_day;
	private LocalDate annual_payment_day;

}
