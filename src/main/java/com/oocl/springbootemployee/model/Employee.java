package com.oocl.springbootemployee.model;

import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private int age;
    private Gender gender;
    private double salary;

    public Employee(int id, String name, int age, Gender gender, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                age == employee.age &&
                salary == employee.salary &&
                Objects.equals(name, employee.name) &&
                gender == employee.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, gender, salary);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public double getSalary() {
        return salary;
    }

    public void setId(int id) {
        this.id = id;
    }

}
