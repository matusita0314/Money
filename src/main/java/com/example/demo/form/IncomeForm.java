package com.example.demo.form;



import java.time.LocalDate;

import lombok.Data;

@Data
public class IncomeForm {
	private Integer amount;
	private LocalDate date;
	private String username;
	private String job;
	private LocalDate remittance_date;
}
