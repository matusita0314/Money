package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "income")
@Data
public class Income {
	
	@Id
	private int income_id;
	@Column(name="user_id")
	private int userId;
	private int amount;
	private Date date;

}
