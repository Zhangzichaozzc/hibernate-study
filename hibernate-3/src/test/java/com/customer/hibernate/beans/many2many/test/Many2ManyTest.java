package com.customer.hibernate.beans.many2many.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.hibernate.beans.many2many.Catalog;
import com.customer.hibernate.beans.many2many.Item;

/**
 * Many2ManyTest
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class Many2ManyTest {

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
    public void testMany2ManySave() {
        Item item = new Item("ITEM-AA");
        Item item2 = new Item("ITEM-BB");

        Catalog catalog = new Catalog();
        catalog.setName("C-AA");
        Catalog catalog2 = new Catalog();
        catalog2.setName("C-BB");

        catalog.getItems().add(item);
        catalog.getItems().add(item2);

        catalog2.getItems().add(item);
        catalog2.getItems().add(item2);

        session.save(item);
        session.save(item2);

        session.save(catalog);
        session.save(catalog2);
    }

    @Test
    public void testGet(){
        Catalog catalog = session.get(Catalog.class, 1);
        System.out.println("catalog.getName() = " + catalog.getName());
        for (Item item : catalog.getItems()) {
            System.out.println(item);
        }
    }

    @Test
    public void testDoubleMany2ManySave(){
        Item item = new Item("ITEM-AA");
        Item item2 = new Item("ITEM-BB");

        Catalog catalog = new Catalog();
        catalog.setName("C-AA");
        Catalog catalog2 = new Catalog();
        catalog2.setName("C-BB");

        catalog.getItems().add(item);
        catalog.getItems().add(item2);

        catalog2.getItems().add(item);
        catalog2.getItems().add(item2);

        item.getCatalogs().add(catalog);
        item.getCatalogs().add(catalog2);
        item2.getCatalogs().add(catalog);
        item2.getCatalogs().add(catalog2);

        session.save(item);
        session.save(item2);

        session.save(catalog);
        session.save(catalog2);
    }
}
