//package com.example.demo.service;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.example.demo.entity.User;
//import com.example.demo.form.ChangeGoalForm;
//import com.example.demo.form.FirstLoginForm;
//import com.example.demo.repository.UserRepository;
//
//public class LoginServiceTest {
//
//	@Mock
//	private UserRepository userrepository;
//
//	@InjectMocks
//	private LoginService loginservice;
//
//	@BeforeEach
//	void setup() {
//		MockitoAnnotations.openMocks(this);
//	}
//
//	/**
//	 * SearchUserByIdの単体テスト
//	 * usernameを引数に入れた時にOptional型のUserインスタンスが返ってくるか確認
//	 */
//
//	@Test
//	void testSearchUserById() {
//		User expected = new User();
//		expected.setUsername("testUser");
//		expected.setPassword("testpassword");
//
//		when(userrepository.findById("testUser")).thenReturn(Optional.of(expected));
//
//		Optional<User> result = loginservice.searchUserById("testUser");
//
//		assertThat(result).isPresent();
//		assertThat(result.get().getUsername()).isEqualTo("testUser");
//		assertThat(result.get().getPassword()).isEqualTo("testpassword");
//		verify(userrepository, times(1)).findById("testUser");
//
//		System.out.println("PASS");
//	}
//
//	/**
//	 * resistFirstInfoの単体テスト
//	 * FirstLoginFormとusernameが引数に渡されたときに
//	 * データベース上にユーザー情報が登録される確認
//	 */
//
//	@Test
//	void testresistFirstInfo() {
//		User user = new User();
//		user.setUsername("testUser");
//
//		FirstLoginForm form = new FirstLoginForm();
//		form.setGoal(100000);
//		form.setSavings(50000);
//
//		when(userrepository.findById("testUser")).thenReturn(Optional.of(user));
//
//		loginservice.resistFirstInfo(form, "testUser");
//
//		assertThat(user.getGoal()).isEqualTo(100000);
//		assertThat(user.getSavings()).isEqualTo(50000);
//		verify(userrepository, times(1)).findById("testUser");
//		/** repositoryのsaveメソッドが一回呼ばれたかどうか確認 */
//		verify(userrepository, times(1)).save(user);
//		
//		System.out.println("PASS");
//	}
//
//	/**
//	 * resistFirstLoginの単体テスト
//	 * ユーザー名を受け取るとユーザーインスタンスの
//	 * FirstLoginのフラグをtrueからfalseに変更するか確認
//	 */
//
//	@Test
//	void testresistFirstLogin() {
//		User user = new User();
//		user.setUsername("testUser");
//		user.setFirstLogin(true);
//
//		when(userrepository.findById("testUser")).thenReturn(Optional.of(user));
//
//		loginservice.resistFirstlogin("testUser");
//
//		assertThat(user.getFirstLogin()).isEqualTo(false);
//		verify(userrepository, times(1)).findById("testUser");
//		verify(userrepository, times(1)).save(user);
//
//		System.out.println("PASS");
//	}
//	
//	/**
//	 * resistGoalの単体テスト
//	 * ChangeGoalFormとユーザーネームを引数を受け取ると、
//	 * ユーザーインスタンスのGoalの値を変更するか確認
//	 */
//	
//	@Test
//	void testresistGoal() {
//		User user = new User();
//		user.setUsername("testUser");
//		user.setGoal(300000);
//		
//		ChangeGoalForm form = new ChangeGoalForm();
//		form.setGoal(400000);
//		
//		when(userrepository.findById("testUser")).thenReturn(Optional.of(user));
//		
//		loginservice.resistGoal(form,"testUser");
//		
//		assertThat(user.getGoal()).isEqualByComparingTo(400000);
//		verify(userrepository, times(1)).findById("testUser");
//		verify(userrepository, times(1)).save(user);
//
//		System.out.println("PASS");
//	}
//
//}
