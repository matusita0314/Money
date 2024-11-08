package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.form.ChangeGoalForm;
import com.example.demo.form.FirstLoginForm;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * ログイン画面 service
 */

@Service
@RequiredArgsConstructor
public class LoginService {
	
	/** ユーザー登録情報DAO */
	private final UserRepository repository;
	
	/**
	 * ユーザー情報テーブルから主キーを用いての検索
	 * 
	 * @param username ユーザーネーム
	 * @return ユーザーネームと一致するテーブル
	 */
	
	public Optional<User> searchUserById(String username){
		return repository.findById(username);
	}
	
	/**
	 * 初回ログイン時の貯金額と目標のDBへの保存
	 * 
	 * @param form
	 * @param usernames
	 */
	
	public void resistFirstInfo(FirstLoginForm form,String username) {
		var user = repository.findById(username);
		user.get().setSavings(form.getSavings());
		user.get().setGoal(form.getGoal());
		repository.save(user.get()); //Loginエンティティの更新
	}
	
	/**
	 * firstloginフラグを0にする
	 * 
	 * @param username
	 */
	
	public void resistFirstlogin(String username) {
		var user = repository.findById(username);
		user.get().setFirstLogin(false);
		repository.save(user.get());
	}
	
	/**
	 * 目標を登録するためのメソッド
	 * 
	 * @param form
	 * @param username
	 */
	
	public void resistGoal(ChangeGoalForm form,String username) {
		var user  = repository.findById(username);
		user.get().setGoal(form.getGoal());
		repository.save(user.get());
	}
	
}
