package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(0, "Lily", 20, Gender.FEMALE, 8000));
        employees.add(new Employee(1, "Tom", 21, Gender.MALE, 9000));
        employees.add(new Employee(2, "Jacky", 19, Gender.MALE, 7000));

    }

    public List<Employee> getAll() {
        return employees;
    }

    public void save(Employee employee) {
        employees.add(employee);
    }
}
