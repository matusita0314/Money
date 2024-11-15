//package com.example.demo.service;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.example.demo.entity.Income;
//import com.example.demo.entity.User;
//import com.example.demo.form.IncomeForm;
//import com.example.demo.repository.IncomeRepository;
//import com.example.demo.repository.UserRepository;
//
//public class IncomeServiceTest {
//
//	@Mock
//	private UserRepository userrepository;
//
//	@Mock
//	private IncomeRepository incomerepository;
//
//	@InjectMocks
//	private IncomeService service;
//
//	@BeforeEach
//	void setup() {
//		MockitoAnnotations.openMocks(this);
//	}
//
//	/**
//	 * searchIncomeBynameの単体テスト
//	 * 引数にユーザーネームを渡したときにリスト型で
//	 * 収入一覧が返ってくるかどうか確認
//	 */
//
//	@Test
//	void testsearchIncomeByname() {
//		User user = new User();
//		user.setUsername("testUser");
//
//		List<Income> incomeList = new ArrayList<>();
//		Income income1 = new Income();
//		income1.setUsername("testUser");
//		income1.setAmount(10000);
//		incomeList.add(income1);
//
//		when(userrepository.findById("testUser")).thenReturn(Optional.of(user));
//		when(incomerepository.findByUsername("testUser")).thenReturn(incomeList);
//
//		List<Income> result = service.searchIncomeByname("testUser");
//
//		assertThat(result.size()).isEqualTo(1);
//		assertThat(result.get(0).getUsername()).isEqualTo("testUser");
//		assertThat(result.get(0).getAmount()).isEqualTo(10000);
//		verify(userrepository, times(1)).findById("testUser");
//		verify(incomerepository, times(1)).findByUsername("testUser");
//
//		System.out.println("PASS");
//	}
//
//	@Test
//	void testresistIncome() {
//		IncomeForm form = new IncomeForm();
//		form.setAmount(10000);
//		Date date = Date.valueOf("2024-03-14");
//		form.setDate(date);
//		form.setUsername("testUser");
//		form.setJob("とと桜");
//
//		doAnswer(invocation -> invocation.getArgument(0)).when(incomerepository).save(any(Income.class));
//
//		service.resistIncome(form);
//
//		ArgumentCaptor<Income> captor = ArgumentCaptor.forClass(Income.class);
//		verify(incomerepository, times(1)).save(captor.capture());
//
//		Income capturedIncome = captor.getValue();
//		assertThat(capturedIncome.getUsername()).isEqualTo("testUser");
//		assertThat(capturedIncome.getAmount()).isEqualTo(10000);
//		assertThat(capturedIncome.getDate()).isEqualTo(date);
//		assertThat(capturedIncome.getJob()).isEqualTo("とと桜");
//		
//		System.out.println("PASS");
//	}
//	
//	@Test
//	void testdeleteIncome() {
//		int income_id = 1;
//		
//		service.deleteIncome(income_id);
//		
//		verify(incomerepository, times(1)).deleteById(income_id);
//		
//		System.out.println("PASS");
//	}
//
//}
