package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
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
	
	public void resistSavings(FirstLoginForm form,String username) {
		var user = repository.findById(username);
		user.get().setSavings(form.getSavings());
		repository.save(user.get()); //Loginエンティティの更新
	}
	
	public void resistFirstlogin(String username) {
		var user = repository.findById(username);
		user.get().setFirstLogin(false);
		repository.save(user.get());
	}
	
}
