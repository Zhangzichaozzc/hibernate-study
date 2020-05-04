package com.customer.hibernate.beans;

/**
 * Order
 *
 * @author Zichao Zhang
 * @date 2020/5/1
 */
public class Order {
    private Integer orderId;
    private String orderName;
    private Customer customer;

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", customer=" + customer +
                '}';
    }

    public Order(String orderName, Customer customer) {
        this.orderName = orderName;
        this.customer = customer;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
