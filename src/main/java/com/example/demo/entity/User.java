package com.example.demo.entity;

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
    private String username;
	@Column(name="mail_address")
    private String mailAddress;
    private String password;
    private int user_id;
    @Column(name = "first_login", columnDefinition = "TINYINT(1)")
    private Boolean firstLogin;
    private Integer savings;
    
}
