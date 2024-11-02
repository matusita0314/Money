package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Income;
import com.example.demo.repository.IncomeRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class IncomeService {
	
	private final UserRepository userrepository;
	
	private final IncomeRepository incomerepository;
	
	public List<Income> searchIncomeById(String username){
		var user = userrepository.findById(username);
		return incomerepository.findByUserId(user.get().getUser_id());
	}

}
