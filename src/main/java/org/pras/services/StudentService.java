package org.pras.services;

import org.hibernate.Transaction;
import org.hibernate.Session;
import org.pras.config.HibernateUtil;
import org.pras.models.Student;

import java.util.List;

import org.pras.models.*;
import org.springframework.stereotype.Service;


@Service
public class StudentService {



    public StudentService() {

    }

    public static void displayReservationNotifications(int studentId) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            String jpql = """
                SELECT r
                FROM Reservation r
                WHERE r.student.studentId = :studentId
                AND r.notified = true
                """;

            List<Reservation> reservations = session.createQuery(jpql, Reservation.class)
                    .setParameter("studentId", studentId)
                    .getResultList();

            if (reservations.isEmpty()) {
                return;
            }

            System.out.println("==================================");
            System.out.println("RESERVATION NOTIFICATION");
            System.out.println("==================================");

            for (Reservation reservation : reservations) {

                System.out.println("Book ID : "
                        + reservation.getBook().getBookId());

                System.out.println("Title : "
                        + reservation.getBook().getTitle());

                System.out.println("Reserved On : "
                        + reservation.getReservationDate());

                System.out.println();
            }

            System.out.println("Your reserved book(s) are now available.");
            System.out.println("Please borrow them as soon as possible.");

        } finally {

            session.close();
        }
    }

    public void registerStudent(Student student) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            Student existingStudent = session.find(Student.class, student.getStudentId());

            if (existingStudent != null) {

                System.out.println("Student ID already exists");

            } else {

                session.persist(student);

                System.out.println("Student registered successfully");
            }

            transaction.commit();

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();

        } finally {

            session.close();
        }
    }

    public Student loginStudent(int studentId, String password) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            String jpql = """
                SELECT s
                FROM Student s
                WHERE s.studentId = :studentId
                AND s.password = :password
                """;

            return session.createQuery(jpql, Student.class)
                    .setParameter("studentId", studentId)
                    .setParameter("password", password)
                    .uniqueResult();

        } finally {

            session.close();
        }
    }

    public Student searchStudentById(int studentId) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            return session.find(Student.class, studentId);

        } finally {

            session.close();
        }
    }

    public void displayAllStudents() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            String jpql = """
                SELECT s
                FROM Student s
                """;

            List<Student> students = session.createQuery(jpql, Student.class)
                    .getResultList();

            if (students.isEmpty()) {

                System.out.println("No students found");
                return;
            }

            for (Student student : students) {

                student.displayProfile();
                System.out.println();
            }

        } finally {

            session.close();
        }
    }

    public void removeStudent(int studentId) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            Student student = session.find(Student.class, studentId);

            if (student == null) {

                System.out.println("Student not found.");
                return;
            }

            String jpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.student.studentId = :studentId
                AND br.returned = false
                """;

            BorrowRecord borrowRecord = session.createQuery(jpql, BorrowRecord.class)
                    .setParameter("studentId", studentId)
                    .setMaxResults(1)
                    .uniqueResult();

            if (borrowRecord != null) {

                System.out.println("Cannot remove the student because they have borrowed book(s).");

            } else {

                session.remove(student);

                System.out.println("Student removed successfully.");
            }

            transaction.commit();

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();

        } finally {

            session.close();
        }
    }

    public void updateStudentDetails(int studentId,
                                     String newName,
                                     String newDepartment) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            Student student = session.find(Student.class, studentId);

            if (student == null) {
                System.out.println("Student not found");
                return;
            }

            student.setName(newName);
            student.setDepartment(newDepartment);

            transaction.commit();

            System.out.println("Student details updated successfully");

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();

        } finally {

            session.close();
        }
    }

    public void resetStudentPassword(int studentId, String newPassword) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            Student student = session.find(Student.class, studentId);

            if (student == null) {

                System.out.println("Student not found");
                return;
            }

            student.setPassword(newPassword);

            transaction.commit();

            System.out.println("Student password reset successfully");

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();

        } finally {

            session.close();
        }
    }
}