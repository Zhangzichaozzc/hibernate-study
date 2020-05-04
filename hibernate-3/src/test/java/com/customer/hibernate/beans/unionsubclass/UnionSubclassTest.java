package com.customer.hibernate.beans.unionsubclass;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * JoinedSubclassTest
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class UnionSubclassTest {
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
    public void testSave(){
        Person person = new Person("P-BB");
        Student student = new Student("S-College");
        student.setName("S-BB");
        session.save(person);
        session.save(student);
    }

}
