package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getAll() {

        return employeeRepository.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Integer id) {
        return employeeRepository.getEmployeeById(id);
    }

    @GetMapping(params = {"gender"})
        public List<Employee> getEmployeeByGender(@RequestParam Gender gender){

        return employeeRepository.getEmployeeByGender(gender);
    }
}