package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Integer>{
	/**List型で返ってくるメソッドを定義*/
	List<Subscription> findByUsername(String username);
	List<Subscription> findAll();
}
