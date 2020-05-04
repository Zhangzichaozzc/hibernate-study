package com.customer.hibernate.beans;

/**
 * Customer
 *
 * @author Zichao Zhang
 * @date 2020/5/1
 */
public class Customer {
    private Integer customerId;
    private String customerName;

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
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
