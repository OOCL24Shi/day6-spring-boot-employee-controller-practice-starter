package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.Companies;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Companies> getAll() {
        return companyRepository.getAll();
    }
}
