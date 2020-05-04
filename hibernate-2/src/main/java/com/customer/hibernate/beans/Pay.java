package com.customer.hibernate.beans;

/**
 * Pay
 *
 * @author Zichao Zhang
 * @date 2020/5/1
 */
public class Pay {
    private Integer monthlyPay;
    private Integer yearPay;
    private Integer vocationWithPay;

    private Worker worker;

    @Override
    public String toString() {
        return "Pay{" +
                "monthlyPay=" + monthlyPay +
                ", yearPay=" + yearPay +
                ", vocationWithPay=" + vocationWithPay +
                '}';
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Pay() {
    }

    public Pay(Integer monthlyPay, Integer yearPay, Integer vocationWithPay) {
        this.monthlyPay = monthlyPay;
        this.yearPay = yearPay;
        this.vocationWithPay = vocationWithPay;
    }

    public Integer getMonthlyPay() {
        return monthlyPay;
    }

    public void setMonthlyPay(Integer monthlyPay) {
        this.monthlyPay = monthlyPay;
    }

    public Integer getYearPay() {
        return yearPay;
    }

    public void setYearPay(Integer yearPay) {
        this.yearPay = yearPay;
    }

    public Integer getVocationWithPay() {
        return vocationWithPay;
    }

    public void setVocationWithPay(Integer vocationWithPay) {
        this.vocationWithPay = vocationWithPay;
    }
}
