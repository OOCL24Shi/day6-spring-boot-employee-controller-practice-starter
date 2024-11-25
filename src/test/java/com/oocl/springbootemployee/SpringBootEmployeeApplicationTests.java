package com.oocl.springbootemployee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class SpringBootEmployeeApplicationTests {

	@Autowired
	private MockMvc client;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private JacksonTester<List<Employee>> json;
	@Autowired
	private JacksonTester<Employee> jsonObject;

	@BeforeEach
	void setUp() {
		employeeRepository.getAll().clear();
		employeeRepository.save(new Employee(0, "Lily", 20, Gender.FEMALE, 8000));
		employeeRepository.save(new Employee(1, "Tom", 21, Gender.MALE, 9000));
		employeeRepository.save(new Employee(2, "Jacky", 19, Gender.MALE, 7000));
	}

	@Test
	public void should_get_employee_list_when_call_get_all_given_no_parameter() throws Exception {
		// given
		List<Employee> employees = employeeRepository.getAll();
		// when
		String responseBody = client.perform(MockMvcRequestBuilders.get("/employees"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
				.andReturn().getResponse().getContentAsString();
		// then
		List<Employee> responseBodyObj = json.parse(responseBody).getObject();
		assertThat(employees).isEqualTo(responseBodyObj);
	}
	//GET /employees/1 # get a specific employee by ID
	@Test
	void should_get_a_specific_employee_when_get_given_get_a_id() throws Exception {
	    //Given
		Employee expectedEmployee = employeeRepository.getAll().get(0);


	    //When
		client.perform(MockMvcRequestBuilders.get("/employees/"+0))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedEmployee.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedEmployee.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(expectedEmployee.getAge()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(expectedEmployee.getSalary()));

	    //Then

	}
}