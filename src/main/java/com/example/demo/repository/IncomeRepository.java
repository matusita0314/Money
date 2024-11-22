package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Income;

@Repository 
public interface IncomeRepository extends JpaRepository<Income,Integer>{
	/**List型で返ってくるメソッドを定義*/
	List<Income> findByUsername(String username) ; 
	
	/** 配列[月、その月の合計収入]という形のリスト型で返す*/
	@Query("SELECT MONTH(i.date) AS month, SUM(i.amount) AS totalIncome FROM Income i WHERE i.username = :username AND i.date >= DATEADD(MONTH, -12, CURRENT_DATE) GROUP BY MONTH(i.date)")
	List<Object[]> findPast12MonthsIncome(@Param("username") String username);
	
	/** 全ユーザーの収入情報をとってくるメソッド */
	List<Income> findAll();

}
