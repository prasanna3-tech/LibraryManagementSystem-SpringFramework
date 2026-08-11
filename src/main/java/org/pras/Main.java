package org.pras;


import org.pras.config.AppConfig;
import org.pras.services.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.pras.config.HibernateUtil;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        BookService bookService =
                context.getBean(BookService.class);

        System.out.println(bookService);

        System.out.println("Spring Container Started");

        LibraryManagementSystem lms = new LibraryManagementSystem();
        HibernateUtil.getSessionFactory();
        lms.start();
    }
}