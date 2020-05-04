package com.customer.hibernate.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.hibernate.beans.Department;
import com.customer.hibernate.beans.Employee;
import com.customer.hibernate.beans.dao.DepartmentDao;
import com.customer.hibernate.beans.util.HibernateUtils;

/**
 * TwoLevelCacheTest
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class TwoLevelCacheTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configure = new Configuration().configure("/hibernate.cfg.xml");
        ServiceRegistry serviceRegistry =
                new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();
        sessionFactory = configure.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void destroy() {
//        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testTwoLevelCache() {
        Employee employee = (Employee) session.get(Employee.class, 1);
        System.out.println("employee = " + employee);
        transaction.commit();
        session.close();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        employee = (Employee) session.get(Employee.class, 1);
        System.out.println("employee = " + employee);
    }

    @Test
    public void testCollectionTwoLevelCache() {
        Department department = (Department) session.get(Department.class, 1);
        for (Employee employee : department.getEmployees()) {
            System.out.println("employee.getName() = " + employee.getName());
        }
        transaction.commit();
        session.close();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        department = (Department) session.get(Department.class, 1);
        for (Employee employee : department.getEmployees()) {
            System.out.println("employee.getName() = " + employee.getName());
        }
    }

    @Test
    public void testQueryCache() {
//        Query query = session.createQuery("from Employee");
//        query.setCacheable(true);
//        List list = query.list();
//        System.out.println(list.size());
//        list = query.list();
//        System.out.println(list.size());

        Criteria criteria = session.createCriteria(Employee.class);
        criteria.setCacheable(true);
        List salary = criteria.add(Restrictions.gt("salary", 1000f)).list();
        System.out.println(salary.size());
        salary = criteria.list();
        System.out.println(salary.size());

    }

    @Test
    public void testManageSession() {
        Department dept = new Department("Linux");
        Session session = HibernateUtils.getInstance().geSession();
        Transaction transaction = session.beginTransaction();
        DepartmentDao departmentDao = new DepartmentDao();
        departmentDao.save(dept);
        // 如果 Session 是由 Thread 管理的，则 Session 在 提交或者回滚之后就会关闭
        transaction.commit();
        System.out.println("session.isOpen() = " + session.isOpen());

    }

}
