package com.customer.hibernate.beans;

/**
 * Employee
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class Employee {
    private Integer id;
    private String name;
    private float salary;
    private String email;

    private Department department;

    public Employee(float salary, String email, Department department) {
        this.salary = salary;
        this.email = email;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", email='" + email + '\'' +
                '}';
    }

    public Employee(String name, float salary, String email) {
        this.name = name;
        this.salary = salary;
        this.email = email;
    }

    public Employee() {
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

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
