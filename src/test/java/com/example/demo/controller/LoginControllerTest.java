package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.constant.MessageConst;
import com.example.demo.service.LoginService;

public class LoginControllerTest {
	
	@Mock
	private LoginService service;
	
	@Mock
	private PasswordEncoder passwordencoder;
	
	@Mock
	private MessageSource messageSource;
	
	@Mock
	private HttpSession session;
	
	@InjectMocks
	private LoginController logincontroller;
	
	private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(logincontroller).build();
    }

    @Test
    void testView() throws Exception {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"));
    }
    
    @Test
    void testviewError() throws Exception {
    	when(messageSource.getMessage(MessageConst.LOGIN_WRONG_INPUT, null, null))
    		.thenReturn("エラーメッセージ");
    	
    	mockMvc.perform(get("/login").param("error",""))
    			.andExpect(status().isOk())
    			.andExpect(view().name("login"))
    			.andExpect(model().attributeExists("errorMsg"));
    }

}
