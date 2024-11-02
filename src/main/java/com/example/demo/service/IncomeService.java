package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Income;
import com.example.demo.repository.IncomeRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class IncomeService {
	
	private final IncomeRepository incomerepository;
	
	public Optional<Income> searchIncomeById(int income_id){
		return incomerepository.findById(income_id);
	}

}
