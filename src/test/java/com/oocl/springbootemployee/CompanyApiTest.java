package com.oocl.springbootemployee;

import com.oocl.springbootemployee.model.Companies;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.json.JacksonTester;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CompanyApiTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JacksonTester<List<Companies>> jsonTester;

    //GET #obtain company list with response of id, name
    @Test
    public void should_get_company_list_when_get_given_no_parameter() throws Exception {
        // given
        List<Companies> companies = companyRepository.getAll();
        // when
        String responseBody = client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andReturn().getResponse().getContentAsString();

        List<Companies> responseBodyObj = jsonTester.parse(responseBody).getObject();
        assertThat(companies).isEqualTo(responseBodyObj);
        // then
    }
}