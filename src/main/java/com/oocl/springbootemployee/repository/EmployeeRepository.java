package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.spi.ToolProvider.findFirst;

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

    public Employee save(Employee employee) {
        employee.setId(employees.size());
        employees.add(employee);
        return employee;
    }

    public Employee getEmployeeById(int id) {

        return (Employee) employees.stream()
                .filter(employee -> employee.getId()==id)
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getEmployeeByGender(Gender gender) {
        return employees.stream()
                .filter(employee -> employee.getGender() == gender)
                .collect(Collectors.toList());
    }
}
