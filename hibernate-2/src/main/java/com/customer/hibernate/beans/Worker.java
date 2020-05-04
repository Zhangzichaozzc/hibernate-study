package com.customer.hibernate.beans;

/**
 * Worker
 *
 * @author Zichao Zhang
 * @date 2020/5/1
 */
public class Worker {
    private Integer id;
    private String name;
    private Pay pay;

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pay=" + pay +
                '}';
    }

    public Worker() {
    }

    public Worker(String name, Pay pay) {
        this.name = name;
        this.pay = pay;
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

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }
}
