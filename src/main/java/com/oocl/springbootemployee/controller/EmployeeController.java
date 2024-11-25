package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;

import java.util.List;

import org.springframework.http.HttpStatus;
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
    public List<Employee> getEmployeeByGender(@RequestParam Gender gender) {

        return employeeRepository.getEmployeeByGender(gender);
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        return employeeRepository.updateEmployee(id, employee);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        employeeRepository.deleteEmployee(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return employeeRepository.getEmployeesByPage(page, pageSize);
    }
}