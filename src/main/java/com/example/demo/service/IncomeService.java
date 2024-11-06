package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Income;
import com.example.demo.form.IncomeForm;
import com.example.demo.repository.IncomeRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class IncomeService {
	
	private final UserRepository userrepository;
	
	private final IncomeRepository incomerepository;
	
	public List<Income> searchIncomeByname(String username){
		var user = userrepository.findById(username);
		return incomerepository.findByUsername(user.get().getUsername());
	}
	
	public void resistIncome(IncomeForm form){
		var income = new Income();
		income.setAmount(form.getAmount());
		income.setDate(form.getDate());
		income.setUsername(form.getUsername());
		income.setJob(form.getJob());
		incomerepository.save(income);
	}
	
	public void deleteIncome(Integer income_id) {
		incomerepository.deleteById(income_id);
	}
}
