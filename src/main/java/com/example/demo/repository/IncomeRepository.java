package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Income;

@Repository 
public interface IncomeRepository extends JpaRepository<Income,Integer>{
	/**List型で返ってくるメソッドを定義*/
	List<Income> findByUsername(String username) ; 
}
