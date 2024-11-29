package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanDefine {
	
	/**
	 * Spring Securityはパスワードの比較を行う際、
	 * 登録されているパスワードはどのようなエンコードになっているのかというのを
	 * PasswordEncoderの@Bean定義で確認する。
	 */
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
