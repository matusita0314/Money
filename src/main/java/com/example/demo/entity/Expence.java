package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "expence")
@Data
public class Expence {
	
	@Id
	private int expence_id;
	private String username;
	private int amount;
	private Date date;
	private String category;

}