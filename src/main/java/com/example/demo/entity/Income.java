package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "income")
@Data
public class Income {
	
	@Id
	private int income_id;
	private String username;
	private Integer amount;
	private LocalDate date;
	private String job;
	private LocalDate remittance_date;

}
