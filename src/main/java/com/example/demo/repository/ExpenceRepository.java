package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Expence;

@Repository 
public interface ExpenceRepository extends JpaRepository<Expence,Integer>{
	List<Expence> findByUsername(String username) ;
}
