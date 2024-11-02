package com.example.demo.controller;

import java.text.Normalizer.Form;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IncomeController {
	
	@GetMapping("/income")
	public String view(Model model,Form form) {
		return "income";
	}
	
	@PostMapping("/income")
	public void calculateincome(Model model,Form form) {
		
	}

}
