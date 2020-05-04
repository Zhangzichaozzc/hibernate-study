package com.customer.hibernate.beans.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * HibernateUtils
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class HibernateUtils {

    private SessionFactory sessionFactory;

    private HibernateUtils() {
    }

    private static HibernateUtils instance = new HibernateUtils();

    public static HibernateUtils getInstance() {
        return instance;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configure = new Configuration().configure("/hibernate.cfg.xml");
            ServiceRegistry serviceRegistry =
                    new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();
            sessionFactory = configure.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    public Session geSession() {
        return this.getSessionFactory().getCurrentSession();
    }
}
