package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository //データベースからUserデータをとってくるクラス
public interface UserRepository extends JpaRepository<User,Integer>{
	
} //この継承で対象テーブルのエンティティクラスを渡す。
  //このリポジトリがどのテーブルと繋げるのかの定義
