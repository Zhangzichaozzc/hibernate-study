package com.customer.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.hibernate.beans.Customer;
import com.customer.hibernate.beans.Order;

/**
 * ManyToOneTest
 *
 * @author Zichao Zhang
 * @date 2020/5/1
 */
public class ManyToOneTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configure = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory =
                configure.buildSessionFactory(new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry());
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void cleanUp() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    /**
     * 在多对一关联关系中，
     * 如果先存储一的一方，再存储多的一方，则会发送 3 条 INSERT 语句
     * 如果先存储多的一方，再存储一的一方，则会发送 3 条 INSERT 语句, 2 条 UPDATE 语句 这是因为在多的一方插入的时候先把外键列插入为空(因为不知道外键列的值是多少）
     * 之后在一的一方插入数据成功之后再跟新一的一方的外键列的值
     * 所以在多对一中，建议先插入一的一方的数据，再插入多的一方的数据
     */
    @Test
    public void testManyToOneSave() {
        Customer customer = new Customer("Python");

        Order django = new Order("Django", customer);
        Order flask = new Order("Flask", customer);

//        session.save(customer);
//        session.save(spring);
//        session.save(mybatis);

        session.save(django);
        session.save(flask);
        session.save(customer);
    }

    /**
     * 多对一查询:
     * 1. 查询多的一方的数据，如果没有用到一的一方的数据，则会懒加载，即不会查询多的一方的数据
     * 2. 只有用到了一的一方的数据，才会查询一的一方的数据表
     * 3. 查询多的一方的数据时，级联属性返回的是一个代理对象
     * 4. 如果在查询多的一方的数据时没有用到一的一方的数据，并且此时 Session 中的缓存失效，再通过多的一方的对象导航到一的方法，则会抛出异常
     */
    @Test
    public void testManyToOneGet() {
        Order order = (Order) session.get(Order.class, 1);
        System.out.println(order.getOrderName());
        System.out.println(order.getCustomer().getClass());
//        session.close();
//        System.out.println("order.getCustomer().getCustomerName() = " + order.getCustomer().getCustomerName());
    }

    @Test
    public void testManyToOneUpdate() {
        Order order = (Order) session.get(Order.class, 1);
//        session.close();
        order.getCustomer().setCustomerName("java");
    }

    /**
     * 在没有设置级联删除的情况下，如果删除一的一方的数据，则会抛出异常并且删除失败，则是因为在多的一方中有一的一方的外键音引用
     */
    @Test
    public void testManyToOneDelete() {
        Customer customer = (Customer) session.get(Customer.class, 1);
        session.delete(customer);
    }

}
