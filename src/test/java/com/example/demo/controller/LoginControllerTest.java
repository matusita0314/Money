//package com.example.demo.controller;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.util.Optional;
//
//import jakarta.servlet.http.HttpSession;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.context.MessageSource;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.WebAttributes;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.example.demo.constant.MessageConst;
//import com.example.demo.entity.User;
//import com.example.demo.form.LoginForm;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.service.LoginService;
//
//public class LoginControllerTest {
//
//	@Mock
//	private LoginService service;
//
//	@Mock
//	private PasswordEncoder passwordencoder;
//
//	@Mock
//	private MessageSource messageSource;
//
//	@Mock
//	private HttpSession session;
//
//	@Mock
//	private UserDetailsService userdetails;
//
//	@Mock
//	private UserRepository userRepository;
//
//	@InjectMocks
//	private LoginController logincontroller;
//
//	private MockMvc mockMvc;
//
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//		mockMvc = MockMvcBuilders.standaloneSetup(logincontroller).build();
//	}
//
//	/**
//	 * loginviewの単体テスト
//	 * @throws Exception
//	 */
//
//	@Test
//	void testView() throws Exception {
//		mockMvc.perform(get("/login"))
//				.andExpect(status().isOk())
//				.andExpect(view().name("loginView"));
//		System.out.println("PASS");
//	}
//
//	/**
//	 * viewErrorの単体テスト
//	 * @throws Exception
//	 */
//
//	@Test
//	void testviewError() throws Exception {
//		when(session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION))
//				.thenReturn(new Exception("エラーメッセージ"));
//
//		mockMvc.perform(get("/login").param("error", ""))
//				.andExpect(status().isOk())
//				.andExpect(view().name("loginView"))
//				.andExpect(model().attributeExists("errorMsg"));
//		System.out.println("PASS");
//	}
//
//	/**
//	 * FirstLoginViewのテスト
//	 * @throws Exception
//	 */
//
//	@Test
//	void testFirstLoginView() throws Exception {
//		mockMvc.perform(get("/first-login"))
//				.andExpect(status().isOk())
//				.andExpect(view().name("firstlogin"));
//		System.out.println("PASS");
//	}
//
//	/**
//	 *  /loginがPOSTされ、成功したときの挙動をテスト
//	 * @throws Exception
//	 */
//
//	@Test
//	void testLogin_Success() throws Exception {
//		LoginForm form = new LoginForm();
//		form.setUsername("testUser");
//		form.setPassword("testPassword");
//
//		User user = new User();
//		user.setUsername("testUser");
//		user.setPassword("encodedPassword");
//
//		when(service.searchUserById("testUser")).thenReturn(Optional.of(user));
//		when(passwordencoder.matches("testPassword", "encodedPassword")).thenReturn(true);
//
//		mockMvc.perform(post("/login")
//				.param("username", "testUser")
//				.param("password", "testPassword"))
//				.andExpect(status().is3xxRedirection()) //HTTP市てーたすコードが3xxの範囲か確認。（返り値がリダイレクトのため）
//				.andExpect(redirectedUrl("/menu"));
//		System.out.println("PASS");
//	}
//
//	/**
//	 * loginがpostされ、失敗したときのテスト
//	 * @throws Exception
//	 */
//
//	@Test
//	void testLogin_Failure() throws Exception {
//		LoginForm form = new LoginForm();
//		form.setUsername("testUser");
//		form.setPassword("testPassword");
//
//		User user = new User();
//		user.setUsername("testUser");
//		user.setPassword("encodedPassword");
//
//		when(service.searchUserById("testUser")).thenReturn(Optional.of(user));
//		when(passwordencoder.matches("testPassword", "encodedPassword")).thenReturn(false);
//		when(messageSource.getMessage(MessageConst.LOGIN_WRONG_INPUT, null, null))
//				.thenReturn("エラーメッセージ");
//
//		mockMvc.perform(post("/login")
//				.param("username", "testUser")
//				.param("password", "testPassword"))
//				.andExpect(status().isOk())
//				.andExpect(view().name("loginView"));
//
//		System.out.println("PASS");
//	}
//
//	/**
//	 * firstloginがpostされ成功したときのテストコード
//	 * savings < goal
//	 * @throws Exception
//	 */
//
////	
////	@Test
////	@WithUserDetails(value = "user1", userDetailsServiceBeanName = "UserDetailsServicelmpl")
////	void testfirstlogin_Success() throws Exception {
////	    FirstLoginForm form = new FirstLoginForm();
////	    form.setGoal(200000);
////	    form.setSavings(100000);
////
////	    mockMvc.perform(post("/first-login")
////	            .sessionAttr("FirstLoginForm", form)
////	            .param("savings", "100000")
////	            .param("goal", "200000"))
////	        .andExpect(status().is3xxRedirection())
////	        .andExpect(redirectedUrl("/menu"));
////	}
//
//}
