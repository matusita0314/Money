package com.example.demo.form;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SubscriptionForm {
	
	private Integer amount;
	private Boolean notify;
	private String subscription_name;
	private String username;
	private String billing_cycle;
	private Integer monthly_payment_day;
	private LocalDate annual_payment_day;

}
