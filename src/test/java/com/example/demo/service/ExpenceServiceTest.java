package com.example.demo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.Expence;
import com.example.demo.entity.User;
import com.example.demo.form.ExpenceForm;
import com.example.demo.repository.ExpenceRepository;
import com.example.demo.repository.UserRepository;

public class ExpenceServiceTest {

	@Mock
	private UserRepository userrepository;

	@Mock
	private ExpenceRepository expencerepository;

	@InjectMocks
	private ExpenceService service;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * searchIncomeBynameの単体テスト
	 * 引数にユーザーネームを渡したときにリスト型で
	 * 収入一覧が返ってくるかどうか確認
	 */

	@Test
	void testsearchExpenceByname() {
		User user = new User();
		user.setUsername("testUser");

		List<Expence> expenceList = new ArrayList<>();
		Expence expence1 = new Expence();
		expence1.setUsername("testUser");
		expence1.setAmount(10000);
		expenceList.add(expence1);

		when(userrepository.findById("testUser")).thenReturn(Optional.of(user));
		when(expencerepository.findByUsername("testUser")).thenReturn(expenceList);

		List<Expence> result = service.searchExpenceByname("testUser");

		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getUsername()).isEqualTo("testUser");
		assertThat(result.get(0).getAmount()).isEqualTo(10000);
		verify(userrepository, times(1)).findById("testUser");
		verify(expencerepository, times(1)).findByUsername("testUser");

		System.out.println("PASS");
	}

	@Test
	void testresistIncome() {
		ExpenceForm form = new ExpenceForm();
		form.setAmount(3000);
		Date date = Date.valueOf("2024-03-14");
		form.setDate(date);
		form.setUsername("testUser");
		form.setCategory("光熱費");

		doAnswer(invocation -> invocation.getArgument(0)).when(expencerepository).save(any(Expence.class));

		service.resistExpence(form);

		ArgumentCaptor<Expence> captor = ArgumentCaptor.forClass(Expence.class);
		verify(expencerepository, times(1)).save(captor.capture());

		Expence capturedExpence = captor.getValue();
		assertThat(capturedExpence.getUsername()).isEqualTo("testUser");
		assertThat(capturedExpence.getAmount()).isEqualTo(3000);
		assertThat(capturedExpence.getDate()).isEqualTo(date);
		assertThat(capturedExpence.getCategory()).isEqualTo("光熱費");
		
		System.out.println("PASS");
	}
	
	@Test
	void testdeleteIncome() {
		int expence_id = 1;
		
		service.deleteExpence(expence_id);
		
		verify(expencerepository, times(1)).deleteById(expence_id);
		
		System.out.println("PASS");
	}

}
