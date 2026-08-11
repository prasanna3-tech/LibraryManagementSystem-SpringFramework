package org.pras;

import org.pras.config.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        LibraryManagementSystem lms = new LibraryManagementSystem();
        HibernateUtil.getSessionFactory();
        lms.start();
    }
}