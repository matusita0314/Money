package com.example.demo.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ExpenceForm {
	
	private int amount;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	private String username;
	private String category;
}
