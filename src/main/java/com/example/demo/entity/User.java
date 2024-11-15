package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {
	@Id
    private String username;
	@Column(name="mail_address")
    private String mailAddress;
    private String password;
    private int user_id;
    @Column(name = "first_login", nullable = false)
    private Boolean firstLogin =true;
    private int savings = 0;
    private int goal = 0;
}
