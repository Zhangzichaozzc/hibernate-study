package com.customer.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Department
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class Department {
    private Integer id;
    private String name;
    private Set<Employee> employees = new HashSet<>();

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Department(String name) {
        this.name = name;
    }

    public Department() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
