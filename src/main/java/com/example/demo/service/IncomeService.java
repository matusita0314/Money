package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Income;
import com.example.demo.form.IncomeForm;
import com.example.demo.repository.IncomeRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * 収入画面 service
 */

@Service
@RequiredArgsConstructor
public class IncomeService {
	
	/** ユーザー情報登録DAO */
	private final UserRepository userrepository;
	
	/** 収入情報登録DAO */
	private final IncomeRepository incomerepository;
	
	/**
	 * Incomeテーブルからユーザー名を用いて検索
	 * リスト型で返す
	 * 
	 * @param username
	 * @return リスト型の収入一覧
	 */
	
	public List<Income> searchIncomeByname(String username){
		var user = userrepository.findById(username);
		return incomerepository.findByUsername(user.get().getUsername());
	}
	
	/**
	 * Incomeテーブルに収入情報を登録する
	 * 
	 * @param form
	 */
	
	public void resistIncome(IncomeForm form){
		var income = new Income();
		income.setAmount(form.getAmount());
		income.setDate(form.getDate());
		income.setUsername(form.getUsername());
		income.setJob(form.getJob());
		incomerepository.save(income);
	}
	
	/**
	 * Incomeテーブルから情報を削除する
	 * 
	 * @param income_id
	 */
	
	public void deleteIncome(Integer income_id) {
		incomerepository.deleteById(income_id);
	}
}
