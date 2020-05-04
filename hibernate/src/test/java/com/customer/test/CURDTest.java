package com.customer.test;

import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

import com.customer.beans.News;

/**
 * CURDTest
 *
 * @author Zichao Zhang
 * @date 2020/4/30
 */
public class CURDTest {
    @Test
    public void testHelloWorld() {
        SessionFactory sessionFactory = null;

        // 如果 Hibernate 的主配置文件的文件名就 叫 hibernate.cfg.xml 则Configuration.configure()可以不传递参数
        Configuration configure = new Configuration().configure();

        // 穿件 ServiceRegistry 对象，在创建 SessionFactory对象的时候会使用到该对象
//        ServiceRegistry serviceRegistry =
//                new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();

        // 通过 configuration 对象创建 SessionFactory 对象
        sessionFactory = configure.buildSessionFactory()/*buildSessionFactory(serviceRegistry)*/;

        // 创建 Session 对象
        Session session = sessionFactory.openSession();

        // 开启事务
        Transaction transaction = session.beginTransaction();

        // 执行保存操作
        News news = new News(null, "西游记", "吴承恩", new Date(new java.util.Date().getTime()));
        session.save(news);

        // 提交事务
        transaction.commit();

        // 关闭 Session
        session.close();

        // 关闭 SessionFactory
        sessionFactory.close();
    }

    @Test
    public void testget() {
        SessionFactory sessionFactory = null;
        Configuration configure = new Configuration().configure();
        sessionFactory = configure.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        News news = session.get(News.class, 2);
        System.out.println("news = " + news);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }


}
