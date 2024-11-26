package com.oocl.springbootemployee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import java.util.stream.Collectors;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
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
        client.perform(MockMvcRequestBuilders.get("/employees/" + 0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedEmployee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedEmployee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(expectedEmployee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(expectedEmployee.getSalary()));

        //Then
    }

    //GET /employees?gender=male # get all male employees
    @Test
    void should_get_all_male_employees_when_get_given_gender_is_male() throws Exception {
        //Given
        List<Employee> maleEmployees = employeeRepository.getAll().stream()
                .filter(employee -> employee.getGender() == Gender.MALE)
                .collect(Collectors.toList());

        //When
        String responseBody = client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender", "MALE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        //Then
        List<Employee> responseBodyObj = json.parse(responseBody).getObject();
        assertThat(maleEmployees).isEqualTo(responseBodyObj);


        //Then

    }

    //POST /employees # create employee response status 201 created
    @Test
    void should_create_employee_when_post_given_employee() throws Exception {
        //Given
        String givenEmployee = "{\n" +
                "\n" +
                "\"id\":3,\n" +
                "\n" +
                "\"name\":\"Lily\",\n" +
                "\n" +
                "\"age\":20,\n" +
                "\n" +
                "\"gender\":\"FEMALE\",\n" +
                "\n" +
                "\"salary\":8000.0\n" +
                "\n" +
                "}";
        Employee givenEmployeeObject = jsonObject.parseObject(givenEmployee);
        //When
        String insertedEmployee = client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenEmployee))
            .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn().getResponse().getContentAsString();
        //Then
        assertThat(jsonObject.parse(insertedEmployee)).usingRecursiveComparison().isEqualTo(givenEmployeeObject);
    }

    //PUT /employees/1 # update an employee age and salary
    @Test
    void should_update_employee_when_put_given_ID() throws Exception {
        //Given
        String updatedEmployee = " {\n" +
                "        \"name\": \"Tom\",\n" +
                "        \"age\": 24,\n" +
                "        \"gender\": \"MALE\",\n" +
                "        \"salary\": 7500.0\n" +
                "    }";
        Employee updatedEmployeeObject = jsonObject.parseObject(updatedEmployee);

        //When
        client.perform(MockMvcRequestBuilders.put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(updatedEmployeeObject.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(updatedEmployeeObject.getSalary()));

        //Then
        Employee employee = employeeRepository.getEmployeeById(1);
        assertThat(employee.getAge()).isEqualTo(updatedEmployeeObject.getAge());
        assertThat(employee.getSalary()).isEqualTo(updatedEmployeeObject.getSalary());
    }

    //DELETE /employees/1 # delete an employee response status 204 no content
    @Test
    void should_delete_an_employee_when_delete_given_id() throws Exception {
        // Given
        Employee employeeToDelete = employeeRepository.getEmployeeById(1);

        // When
        client.perform(MockMvcRequestBuilders.delete("/employees/" + employeeToDelete.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Then
        assertThat(employeeRepository.getEmployeeById(employeeToDelete.getId())).isNull();
    }
    //GET /employees?page=1&size=5 # Page query, page equals 1, size equals 5
    @Test
    void should_get_employees_with_pagination_when_get_given_page_and_size() throws Exception {
        // Given
        int page = 1;
        int size = 5;
        List<Employee> expectedEmployees = employeeRepository.getEmployeesByPage(page,size);

        // When
        String responseBody = client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Then
        List<Employee> responseBodyObj = json.parse(responseBody).getObject();
        assertThat(responseBodyObj).isEqualTo(expectedEmployees);
    }
}