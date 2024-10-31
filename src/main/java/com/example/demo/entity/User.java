package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
	
	@Id
    private Integer user_id; //自動インクリメント
	
	@Column(name="mail_address")
    private String mailAddress;
	
    private String password;
    private String username;
    private LocalDate birthday;
    
}
