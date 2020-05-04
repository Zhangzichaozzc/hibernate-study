package com.customer.hibernate.beans.one2one.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.hibernate.beans.one2one.primary.Department;
import com.customer.hibernate.beans.one2one.primary.Manager;

/**
 * One2OneTest
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class One2OnePrimaryTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configure = new Configuration().configure();
        sessionFactory = configure.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void cleanUp() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testOne2OneSave() {
        Department department = new Department();
        department.setDeptName("BB");

        Manager manager = new Manager();
        manager.setMgrName("MGR-BB");

        department.setMgr(manager);
        manager.setDept(department);

        session.save(department);
        session.save(manager);
    }

    @Test
    public void testOne2OneGet(){
        Department department = session.get(Department.class, 1);
        System.out.println(department.getDeptName());
        System.out.println("department.getMgr().getMgrName() = " + department.getMgr().getMgrName());
//        Manager manager = session.get(Manager.class, 1);
//        System.out.println("manager.getMgrName() = " + manager.getMgrName());
//        System.out.println("manager.getDept().getDeptName() = " + manager.getDept().getDeptName());
    }

}
