package com.customer.hibernate.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.hibernate.beans.n21.Customer;
import com.customer.hibernate.beans.n21.Order;


/**
 * OneToManyTest
 *
 * @author Zichao Zhang
 * @date 2020/5/1
 */
public class OneToManyTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configure = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry =
                new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();
        sessionFactory = configure.buildSessionFactory(serviceRegistry);
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
     * 在一对多双向关联的时候进行插入:
     * 1. 如果先插入一的一方的数据，则会发送 3 条 INSERT 语句, 2 条 UPDATE 语句，这是由于双方都维护了 关联关系
     * 2. 如果先插入多的一方的数据，则会发送 3 条 INSERT 语句, 4 条 UPDATE 语句，这是由于双方都维护了 关联关系
     * 解决方案:
     * 建议在一的一方的映射文件 <set> 元素中添加 inverse = true 属性，则关联属性由多的一方进行维护
     * 这样，在线插入一的一方的数据再插入多的一方的数据就只会插入 3 条 INSERT 语句，不会有多余的 UPDATE 语句了
     */
    @Test
    public void testOneTwoManySave() {
        Customer customer = new Customer("C Lange");

        Order order1 = new Order("Nginx", customer);
        Order order2 = new Order("Redis", customer);

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        session.save(order1);
        session.save(order2);
        session.save(customer);
    }

    /**
     * 在双向一对多中,需要注意的事项:
     * 1. 在查询中，如果没有用到级联属性，则不会进行级联属性的表的查询
     * 2. 只有用到了级联属性，才会进行级联属性对应表的查找
     * 3. 之前没有用到级联属性的信息，如果在查询过程中清空了 session 中的缓存数据，再查询级联属性的信息，则会抛出 拉加载异常
     * 4. 级联属性返回的类型为 Hibernate 内置的 Set 的实现类，可以支持拉加载，因此在定义集合类型的时候需要定义为接口的类型
     */
    @Test
    public void testOneTwoManyGet() {
        Customer customer = (Customer) session.get(Customer.class, 3);
        System.out.println("customer.getCustomerName() = " + customer.getCustomerName());
        customer.getOrders().forEach(System.out::println);
    }

    @Test
    public void testOneTwoManyUpdate() {
        Customer customer = (Customer) session.get(Customer.class, 2);
        customer.getOrders().iterator().next().setOrderName("Hibernate");
    }

    @Test
    public void testOneTwoManyDelete() {
        Customer customer = (Customer) session.get(Customer.class, 2);
//        session.delete(customer);
        customer.getOrders().clear();
    }

    @Test
    public void testOneTwoManyCascade(){
        Customer customer = new Customer("C++");

        Order order1 = new Order("Windows", customer);
        Order order2 = new Order("MFS", customer);

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        session.save(customer);
    }

    @Test
    public void testOneTwoManyOrderBy(){
        Customer customer = (Customer) session.get(Customer.class, 3);
        System.out.println("customer = " + customer);
    }


}
