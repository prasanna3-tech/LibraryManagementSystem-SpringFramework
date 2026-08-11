package org.pras.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pras.config.HibernateUtil;
import org.pras.models.Admin;
import org.pras.models.Student;
import org.pras.models.SystemSettings;

import java.util.List;

public class AdminService {

    public AdminService( ) {
    }

    public void addAdmin(Admin admin) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            // Check Duplicate Username

            String jpql = """
                SELECT a
                FROM Admin a
                WHERE a.username = :username
                """;

            Query<Admin> query =
                    session.createQuery(
                            jpql,
                            Admin.class
                    );

            query.setParameter(
                    "username",
                    admin.getUsername()
            );

            Admin existingAdmin =
                    query.uniqueResult();

            if (existingAdmin != null) {

                System.out.println("Username already exists");
                return;

            }

            // Begin Transaction

            transaction = session.beginTransaction();

            // Save Admin

            session.persist(admin);

            transaction.commit();

            System.out.println("Admin added successfully");

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

    public Admin loginAdmin(String username, String password) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            String jpql = """
                SELECT a
                FROM Admin a
                WHERE a.username = :username
                AND a.password = :password
                """;

            Query<Admin> query =
                    session.createQuery(
                            jpql,
                            Admin.class
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

    public void removeAdmin(int adminId) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            Admin admin =
                    session.find(Admin.class, adminId);

            if (admin == null) {

                System.out.println("Admin not found");
                return;

            }

            transaction = session.beginTransaction();

            session.remove(admin);

            transaction.commit();

            System.out.println("Admin removed successfully");

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
    public void displayAllAdmins() {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            String jpql = """
                SELECT a
                FROM Admin a
                """;

            Query<Admin> query =
                    session.createQuery(
                            jpql,
                            Admin.class
                    );

            List<Admin> admins =
                    query.getResultList();

            if (admins.isEmpty()) {

                System.out.println("No admins found");
                return;

            }

            for (Admin admin : admins) {

                admin.displayAdminDetails();
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

    public void resetStudentPassword(int studentId, String newPassword) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            Student student =
                    session.find(Student.class, studentId);

            if (student == null) {

                System.out.println("Student not found");
                return;

            }

            transaction = session.beginTransaction();

            student.setPassword(newPassword);

            transaction.commit();

            System.out.println("Student password reset successfully");

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

    public void updateSystemSettings(double finePerDay,
                                     int maxBooksAllowed,
                                     int borrowDurationDays,
                                     int maxRenewCount) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            SystemSettings settings =
                    session.find(SystemSettings.class, 1);

            if (settings == null) {

                System.out.println("System settings not found");
                return;

            }

            transaction = session.beginTransaction();

            settings.setFinePerDay(finePerDay);
            settings.setMaxBooksAllowed(maxBooksAllowed);
            settings.setBorrowDurationDays(borrowDurationDays);
            settings.setMaxRenewCount(maxRenewCount);

            transaction.commit();

            System.out.println("System settings updated successfully");

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
}