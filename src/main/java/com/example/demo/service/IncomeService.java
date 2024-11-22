package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Income;
import com.example.demo.form.AddSavingsForm;
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
	
	/** 収入情報登録DAO */
	private final IncomeRepository incomerepository;
	
	/** ユーザー登録情報DAO */
	private final UserRepository userrepository;
	
	/**
	 * Incomeテーブルからユーザー名を用いて検索
	 * リスト型で返す
	 * 
	 * @param username
	 * @return リスト型の収入一覧
	 */
	
	public List<Income> searchIncomeByname(String username){
		return incomerepository.findByUsername(username);
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
		income.setRemittance_date(form.getRemittance_date());
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
	
	/**
	 * データベースから取得した月と収入データを成型する
	 * @param username
	 * @return
	 */
	
	public List<Integer> getMonthlyIncome(String username){
		List<Object[]> rawData = incomerepository.findPast12MonthsIncome(username);
		List<Integer> monthlyIncome = new ArrayList<>(12);
		/** 配列の初期化 */
		for (int i = 0;i < 12; i++) {
			monthlyIncome.add(0);
		}
		/** データベースからのデータをint型に変換 */
		for(Object[] row : rawData) {
			int month = (int) row[0] - 1;
			int totalIncome = ((Number) row[1]).intValue();
			monthlyIncome.set(month, totalIncome);
		}
		
		return monthlyIncome;
	}
	
	/**
	 * 全てのユーザーの収入情報をリスト型で返す
	 * @return
	 */
	
	public List<Income> findAllUserIncomes(){
		return incomerepository.findAll();
	}
	
	/**
	 * 収入の振込日になると貯金額に反映させるメソッド
	 * @param form
	 * @param username
	 */
	
	public void AddIncomeToSavings(AddSavingsForm form,String username) {
		var user = userrepository.findById(username);
		/** ユーザーの貯金額とフォームの金額を足して貯金額に反映 */
		user.get().setSavings(user.get().getSavings() + form.getSavings());
		userrepository.save(user.get());
	}
}
