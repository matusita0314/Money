package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "subscription")
@Data
public class Subscription {
	
	@Id
	private int subscription_id;
	
	private String username;
	private Boolean notify = true;
	private String subscription_name;
	private int amount;
	private String billing_cycle;
	private int monthly_payment_day;
	private LocalDate annual_payment_day;

}
