package org.pras.config;

import org.hibernate.SessionFactory;

public class HibernateConnectionTest {

    public static void main(String[] args) {

        SessionFactory sessionFactory = org.pras.config.HibernateUtil.getSessionFactory();

        if (sessionFactory != null) {
            System.out.println("Hibernate Connected Successfully");
        } else {
            System.out.println("Connection Failed");
        }

        sessionFactory.close();
    }
}