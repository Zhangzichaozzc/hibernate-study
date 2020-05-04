package com.customer.hibernate.beans.n21;

import java.util.HashSet;
import java.util.Set;

/**
 * Customer
 *
 * @author Zichao Zhang
 * @date 2020/5/1
 */
public class Customer {
    private Integer customerId;
    private String customerName;
    // 1. 在设置类型的时候，需要设置为 接口类型，不能设置为 JavaSE 的标准实现类
    //      因为在Hibernate 获取类型的时候，获取的是 Hibernate 内置的实现类
    // 2. 一的一方在设置多的一方的集合的时候需要进行初始化，这样在设置属性的时候不会发生 NPE
    private Set<Order> orders = new HashSet<>();

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", orders=" + orders +
                '}';
    }

    public Customer() {
    }

    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
