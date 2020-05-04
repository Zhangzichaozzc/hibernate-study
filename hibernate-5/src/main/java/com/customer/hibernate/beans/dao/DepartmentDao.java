package com.customer.hibernate.beans.dao;

import org.hibernate.Session;

import com.customer.hibernate.beans.Department;
import com.customer.hibernate.beans.util.HibernateUtils;

/**
 * DepartmentDao
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class DepartmentDao {

    public void save(Department department) {
        Session session = HibernateUtils.getInstance().geSession();
        session.save(department);
    }

}
