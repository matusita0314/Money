package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Expence;

@Repository 
public interface ExpenceRepository extends JpaRepository<Expence,Integer>{
	List<Expence> findByUsername(String username) ;
	/** 配列[月、その月の合計支出]という形のリスト型で返す*/
	@Query("SELECT MONTH(i.date) AS month, SUM(i.amount) AS totalExpence FROM Expence i WHERE i.username = :username AND i.date >= DATEADD(MONTH, -12, CURRENT_DATE) GROUP BY MONTH(i.date)")
	List<Object[]> findPast12MonthsExpence(@Param("username") String username);
}
