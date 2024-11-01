package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.constant.UrlConst;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
		
		//自作のログイン画面への遷移、ログイン後の遷移画面の指定
		http
			.authorizeHttpRequests(
					authorize -> authorize.requestMatchers(UrlConst.NO_AUTHENTICATION).permitAll()
										  .anyRequest().authenticated())
		
			.formLogin(
					login -> login.loginPage(UrlConst.LOGIN)
				                  .defaultSuccessUrl(UrlConst.MENU));
		
		
		return http.build();
	}
}
