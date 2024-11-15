package com.example.demo.form;



import java.time.LocalDate;

import lombok.Data;

@Data
public class IncomeForm {
	private int amount;
	private LocalDate date;
	private String username;
	private String job;
}
