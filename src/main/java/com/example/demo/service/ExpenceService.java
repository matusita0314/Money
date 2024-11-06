package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Expence;
import com.example.demo.form.ExpenceForm;
import com.example.demo.repository.ExpenceRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ExpenceService {
	
	private final UserRepository userrepository;
	
	private final ExpenceRepository expencerepository;
	
	public List<Expence> searchExpenceByname(String username){
		var user = userrepository.findById(username);
		return expencerepository.findByUsername(user.get().getUsername());
	}
	
	public void resistExpence(ExpenceForm form){
		var expence = new Expence();
		expence.setAmount(form.getAmount());
		expence.setDate(form.getDate());
		expence.setUsername(form.getUsername());
		expence.setCategory(form.getCategory());
		expencerepository.save(expence);
	}
	
	public void deleteExpence(Integer expence_id) {
		expencerepository.deleteById(expence_id);
	}
	
}
