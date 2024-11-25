package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Companies;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    final List<Companies> companies = new ArrayList<>();

    public CompanyRepository() {
        companies.add(new Companies(123, "OOCL"));
        companies.add(new Companies(456, "THOUGHTWORKS"));
    }

    public List<Companies> getAll(){
        return companies;
    }
}
