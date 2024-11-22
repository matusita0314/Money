package com.example.demo.form;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ExpenceForm {
	
	private Integer amount;
	private LocalDate date;
	private String username;
	private String category;
}
