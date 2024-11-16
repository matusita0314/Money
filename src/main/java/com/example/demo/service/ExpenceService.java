package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Expence;
import com.example.demo.form.ExpenceForm;
import com.example.demo.repository.ExpenceRepository;

import lombok.RequiredArgsConstructor;


/**
 * 支出画面 service
 */

@Service
@RequiredArgsConstructor
public class ExpenceService {
	
	/** 支出登録情報DAO */
	private final ExpenceRepository expencerepository;
	
	/**
	 * Expenceテーブルからユーザー名を用いて検索
	 * リスト型で返す
	 * 
	 * @param username
	 * @return リスト型の支出
	 */
	
	public List<Expence> searchExpenceByname(String username){
		return expencerepository.findByUsername(username);
	}
	
	/**
	 * Expenceテーブルに支出情報を登録する
	 * 
	 * @param form
	 */
	
	public void resistExpence(ExpenceForm form){
		var expence = new Expence();
		expence.setAmount(form.getAmount());
		expence.setDate(form.getDate());
		expence.setUsername(form.getUsername());
		expence.setCategory(form.getCategory());
		expencerepository.save(expence);
	}
	
	/**
	 * Expenceテーブルから情報を削除する
	 * 
	 * @param expence_id
	 */
	
	public void deleteExpence(Integer expence_id) {
		expencerepository.deleteById(expence_id);
	}
	
	/**
	 * データベースから取得した月と支出データを成型する
	 * @param username
	 * @return
	 */
	
	public List<Integer> getMonthlyExpence(String username){
		List<Object[]> rawData = expencerepository.findPast12MonthsExpence(username);
		List<Integer> monthlyExpence = new ArrayList<>(12);
		/** 配列の初期化 */
		for (int i = 0;i < 12; i++) {
			monthlyExpence.add(0);
		}
		/** データベースからのデータをint型に変換 */
		for(Object[] row : rawData) {
			int month = (int) row[0] - 1;
			int totalExpence = ((Number) row[1]).intValue();
			monthlyExpence.set(month, totalExpence);
		}
		
		return monthlyExpence;
	}
	
}
