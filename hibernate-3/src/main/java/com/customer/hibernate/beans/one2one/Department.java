package com.customer.hibernate.beans.one2one;

/**
 * Department
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class Department {
    private Integer deptId;
    private String deptName;
    private Manager mgr;

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", mgr=" + mgr +
                '}';
    }

    public Department() {
    }

    public Department(String deptName, Manager mgr) {
        this.deptName = deptName;
        this.mgr = mgr;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Manager getMgr() {
        return mgr;
    }

    public void setMgr(Manager mgr) {
        this.mgr = mgr;
    }
}
