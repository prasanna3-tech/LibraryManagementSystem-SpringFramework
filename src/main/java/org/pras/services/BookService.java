package org.pras.services;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pras.config.HibernateUtil;
import org.pras.models.Book;
import org.pras.models.BorrowRecord;
import java.util.List;

public class BookService {

    public BookService() {
        
    }

    public void addBook(Book newBook) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            String jpql = """
                SELECT b
                FROM Book b
                WHERE b.isbn = :isbn
                """;

            Book existingBook = session.createQuery(jpql, Book.class)
                    .setParameter("isbn", newBook.getIsbn())
                    .uniqueResult();

            if (existingBook != null) {

                existingBook.setQuantity(
                        existingBook.getQuantity() + newBook.getQuantity()
                );

                System.out.println("Book already exists. Quantity updated successfully");

            } else {

                session.persist(newBook);

                System.out.println("Book added successfully");
            }

            transaction.commit();

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();

        } finally {

            session.close();
        }
    }

    public Book searchBookById(int bookId) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            return session.find(Book.class, bookId);

        } finally {

            session.close();
        }
    }

    public ArrayList<Book> searchBooksByKeyword(String keyword) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            String jpql = """
                SELECT b
                FROM Book b
                WHERE b.title LIKE :keyword
                OR b.author LIKE :keyword
                OR b.category LIKE :keyword
                """;

            return new ArrayList<>(
                    session.createQuery(jpql, Book.class)
                            .setParameter("keyword", "%" + keyword + "%")
                            .getResultList()
            );

        } finally {

            session.close();
        }
    }

    public void removeBook(int bookId) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            Book book = session.find(Book.class, bookId);

            if (book == null) {
                System.out.println("Book not found.");
                return;
            }

            String jpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.book.bookId = :bookId
                AND br.returned = false
                """;

            BorrowRecord borrowRecord = session.createQuery(jpql, BorrowRecord.class)
                    .setParameter("bookId", bookId)
                    .setMaxResults(1)
                    .uniqueResult();

            if (borrowRecord != null) {

                System.out.println("Cannot remove the book because it is currently borrowed.");

            } else {

                session.remove(book);
                System.out.println("Book removed successfully.");
            }

            transaction.commit();

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();

        } finally {

            session.close();
        }
    }

    public void updateBook(Book updatedBook) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            Book existingBook = session.find(Book.class, updatedBook.getBookId());

            if (existingBook == null) {
                System.out.println("Book not found");
                return;
            }

            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setIsbn(updatedBook.getIsbn());
            existingBook.setCategory(updatedBook.getCategory());
            existingBook.setQuantity(updatedBook.getQuantity());

            transaction.commit();

            System.out.println("Book updated successfully");

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();

        } finally {

            session.close();
        }
    }

    public void displayAllBooks() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            String jpql = """
                SELECT b
                FROM Book b
                """;

            List<Book> books = session.createQuery(jpql, Book.class)
                    .getResultList();

            if (books.isEmpty()) {
                System.out.println("No books found");
                return;
            }

            for (Book book : books) {
                book.displayBookDetails();
                System.out.println();
            }

        } finally {

            session.close();
        }
    }

}