package org.pras.services;



import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pras.config.HibernateUtil;
import org.pras.models.Librarian;

import java.util.List;

public class LibrarianService {

    public LibrarianService() {
    }

    public void addLibrarian(Librarian librarian) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            // Check Duplicate Username

            String jpql = """
                SELECT l
                FROM Librarian l
                WHERE l.username = :username
                """;

            Query<Librarian> query =
                    session.createQuery(
                            jpql,
                            Librarian.class
                    );

            query.setParameter(
                    "username",
                    librarian.getUsername()
            );

            Librarian existingLibrarian =
                    query.uniqueResult();

            if (existingLibrarian != null) {

                System.out.println("Username already exists");
                return;

            }

            // Begin Transaction

            transaction = session.beginTransaction();

            // Save Librarian

            session.persist(librarian);

            transaction.commit();

            System.out.println("Librarian added successfully");

        }
        catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

            }

            e.printStackTrace();

        }
        finally {

            session.close();

        }

    }

    public Librarian loginLibrarian(String username, String password) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            String jpql = """
                SELECT l
                FROM Librarian l
                WHERE l.username = :username
                AND l.password = :password
                """;

            Query<Librarian> query =
                    session.createQuery(
                            jpql,
                            Librarian.class
                    );

            query.setParameter("username", username);
            query.setParameter("password", password);

            return query.uniqueResult();

        }
        catch (Exception e) {

            e.printStackTrace();
            return null;

        }
        finally {

            session.close();

        }

    }

    public void removeLibrarian(int librarianId) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            Librarian librarian =
                    session.find(Librarian.class, librarianId);

            if (librarian == null) {

                System.out.println("Librarian not found");
                return;

            }

            transaction = session.beginTransaction();

            session.remove(librarian);

            transaction.commit();

            System.out.println("Librarian removed successfully");

        }
        catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

            }

            e.printStackTrace();

        }
        finally {

            session.close();

        }

    }

    public void updateLibrarianDetails(int librarianId,
                                       String newName,
                                       String newUsername,
                                       String newPassword) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            Librarian librarian =
                    session.find(Librarian.class, librarianId);

            if (librarian == null) {

                System.out.println("Librarian not found");
                return;

            }

            // Check if the new username is already used
            String jpql = """
                SELECT l
                FROM Librarian l
                WHERE l.username = :username
                AND l.librarianId <> :librarianId
                """;

            Query<Librarian> query =
                    session.createQuery(jpql, Librarian.class);

            query.setParameter("username", newUsername);
            query.setParameter("librarianId", librarianId);

            Librarian existingLibrarian = query.uniqueResult();

            if (existingLibrarian != null) {

                System.out.println("Username already exists");
                return;

            }

            transaction = session.beginTransaction();

            librarian.setName(newName);
            librarian.setUsername(newUsername);
            librarian.setPassword(newPassword);

            transaction.commit();

            System.out.println("Librarian details updated successfully");

        }
        catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();

        }
        finally {

            session.close();

        }

    }
    public void displayAllLibrarians() {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            String jpql = """
                SELECT l
                FROM Librarian l
                """;

            Query<Librarian> query =
                    session.createQuery(
                            jpql,
                            Librarian.class
                    );

            List<Librarian> librarians =
                    query.getResultList();

            if (librarians.isEmpty()) {

                System.out.println("No librarians found");
                return;

            }

            for (Librarian librarian : librarians) {

                librarian.displayLibrarianDetails();
                System.out.println();

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }
        finally {

            session.close();

        }

    }
}