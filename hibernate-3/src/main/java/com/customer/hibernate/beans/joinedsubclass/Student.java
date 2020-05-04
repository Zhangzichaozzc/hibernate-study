package com.customer.hibernate.beans.joinedsubclass;

/**
 * Student
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class Student extends Person {
    private String school;

    public Student(String school) {
        this.school = school;
    }

    public Student() {
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
