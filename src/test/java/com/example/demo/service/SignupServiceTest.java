package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.User;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.UserRepository;

public class SignupServiceTest {

	@Mock
	private UserRepository repository;

	@Mock
	private PasswordEncoder passwordencoder;

	@InjectMocks
	private SignupService service;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * resistUserの単体テスト(重複していない時)
	 * SignupFormのインスタンスを受け取った際に、
	 * 重複していなかった場合userインスタンスに登録する
	 * その際パスワードは暗号化されたか確認
	 */

	@Test
	void testresistUser() {
		User user = new User();
		user.setUsername("testUser");
		user.setMailAddress("test");

		SignupForm form = new SignupForm();
		form.setMail_address("test");
		form.setPassword("testpwd");
		form.setUsername("testUser");

		when(repository.findById("testUser")).thenReturn(Optional.empty());
		when(passwordencoder.encode("testpwd")).thenReturn("encodedpassword");
		doAnswer(invocation -> {
			User savedUser = invocation.getArgument(0);
			assertThat(savedUser.getPassword()).isEqualTo("encodedpassword");
			return savedUser;
		}).when(repository).save(any(User.class));

		Optional<User> result = service.resistUser(form);

		assertThat(result).isPresent();
		assertThat(result.get().getMailAddress()).isEqualTo("test");
		assertThat(result.get().getPassword()).isEqualTo("encodedpassword");
		assertThat(result.get().getUsername()).isEqualTo("testUser");
		verify(repository, times(1)).findById("testUser");
		verify(repository, times(1)).save(any(User.class));
		verify(passwordencoder, times(1)).encode("testpwd");

		System.out.println("PASS");
	}

	/**
	 * 重複していた場合
	 */

	@Test
	void testresistDeplicateUser() {
		SignupForm form = new SignupForm();
		form.setUsername("testUser");

		User existuser = new User();
		existuser.setUsername("testUser");

		when(repository.findById("testUser")).thenReturn(Optional.of(existuser));

		Optional<User> result = service.resistUser(form);

		assertThat(result).isEqualTo(Optional.empty());
		verify(repository, times(1)).findById("testUser");
		verify(repository, never()).save(any(User.class));
		verify(passwordencoder, never()).encode(anyString());

		System.out.println("PASS");
	}

}
