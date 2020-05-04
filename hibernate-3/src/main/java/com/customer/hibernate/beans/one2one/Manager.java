package com.customer.hibernate.beans.one2one;

/**
 * Manager
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class Manager {
    private Integer mgrId;
    private String mgrName;
    private Department dept;

    @Override
    public String toString() {
        return "Manager{" +
                "mgrId=" + mgrId +
                ", mgrName='" + mgrName + '\'' +
                '}';
    }

    public Manager() {
    }

    public Manager(String mgrName, Department dept) {
        this.mgrName = mgrName;
        this.dept = dept;
    }

    public Integer getMgrId() {
        return mgrId;
    }

    public void setMgrId(Integer mgrId) {
        this.mgrId = mgrId;
    }

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}
