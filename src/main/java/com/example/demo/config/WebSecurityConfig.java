package com.example.demo.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.constant.UrlConst;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final PasswordEncoder passwordencoder;
	
	private final UserDetailsService userDetailsService;
	
	private final MessageSource messageSource;
	
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
	
	/**
	 * Provider定義
	 * @return カスタマイズProvider情報
	 */
	
	@Bean
	AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordencoder);
		provider.setMessageSource(messageSource);
		
		return provider;
		}
}
