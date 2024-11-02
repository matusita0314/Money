package com.example.demo.controller;

import java.text.Normalizer.Form;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Income;
import com.example.demo.form.IncomeForm;
import com.example.demo.service.IncomeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IncomeController {
	
	private final IncomeService incomeservice;  
	
	@GetMapping("/income")
	public String view(Model model,@AuthenticationPrincipal User user,IncomeForm form) {
		List<Income> incomes = incomeservice.searchIncomeById(user.getUsername());
		model.addAttribute("incomes",incomes);
		return "income";
	}
	
	@PostMapping("/income")
	public void calculateincome(Model model,Form form) {
		
	}

}
