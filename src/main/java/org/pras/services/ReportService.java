package org.pras.services;

import java.sql.*;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.pras.config.HibernateUtil;
import org.pras.models.BorrowRecord;
import org.pras.utils.DateUtilNew;
import org.springframework.stereotype.Service;


@Service
public class ReportService {



    public ReportService() {


    }

    public void displayLibraryReport(Date todayDate) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            // Total Books
            Long totalBooks = session.createQuery("""
                SELECT COUNT(b)
                FROM Book b
                """, Long.class)
                    .uniqueResult();

            // Total Students
            Long totalStudents = session.createQuery("""
                SELECT COUNT(s)
                FROM Student s
                """, Long.class)
                    .uniqueResult();

            // Total Librarians
            Long totalLibrarians = session.createQuery("""
                SELECT COUNT(l)
                FROM Librarian l
                """, Long.class)
                    .uniqueResult();

            // Total Borrowed Books
            Long totalBorrowedBooks = session.createQuery("""
                SELECT COUNT(br)
                FROM BorrowRecord br
                WHERE br.returned = false
                """, Long.class)
                    .uniqueResult();

            // Total Overdue Books
            List<Date> dueDates = session.createQuery("""
                SELECT br.dueDate
                FROM BorrowRecord br
                WHERE br.returned = false
                """, Date.class)
                    .getResultList();

            int totalOverdueBooks = 0;

            for (Date dueDate : dueDates) {

                if (DateUtilNew.getLateDays(dueDate, todayDate) > 0) {

                    totalOverdueBooks++;

                }

            }

            // Total Fine Collected
            Double totalFineCollected = session.createQuery("""
                SELECT COALESCE(SUM(br.fine), 0)
                FROM BorrowRecord br
                WHERE br.finePaid = true
                """, Double.class)
                    .uniqueResult();

            System.out.println("========== Library Report ==========");
            System.out.println("Total Books           : " + totalBooks);
            System.out.println("Total Students        : " + totalStudents);
            System.out.println("Total Librarians      : " + totalLibrarians);
            System.out.println("Total Borrowed Books  : " + totalBorrowedBooks);
            System.out.println("Total Overdue Books   : " + totalOverdueBooks);
            System.out.println("Total Fine Collected  : Rs. " + totalFineCollected);

        }
        catch (Exception e) {

            e.printStackTrace();

        }
        finally {

            session.close();

        }

    }

    public void displayAllBorrowRecords() {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            String jpql = """
                SELECT br
                FROM BorrowRecord br
                ORDER BY br.issueDate DESC
                """;

            Query<BorrowRecord> query =
                    session.createQuery(
                            jpql,
                            BorrowRecord.class
                    );

            List<BorrowRecord> borrowRecords =
                    query.getResultList();

            if (borrowRecords.isEmpty()) {

                System.out.println("No borrow records found.");
                return;

            }

            for (BorrowRecord borrowRecord : borrowRecords) {

                System.out.println("========================================");
                System.out.println("Borrow ID      : "
                        + borrowRecord.getBorrowId());

                System.out.println("Student ID     : "
                        + borrowRecord.getStudent().getStudentId());

                System.out.println("Student Name   : "
                        + borrowRecord.getStudent().getName());

                System.out.println("Book ID        : "
                        + borrowRecord.getBook().getBookId());

                System.out.println("Book Title     : "
                        + borrowRecord.getBook().getTitle());

                System.out.println("Issue Date     : "
                        + borrowRecord.getIssueDate());

                System.out.println("Due Date       : "
                        + borrowRecord.getDueDate());

                System.out.println("Return Date    : "
                        + borrowRecord.getReturnDate());

                System.out.println("Returned       : "
                        + borrowRecord.isReturned());

                System.out.println("Renew Count    : "
                        + borrowRecord.getRenewCount());

                System.out.println("Fine           : Rs. "
                        + borrowRecord.getFine());

                System.out.println("Fine Paid      : "
                        + borrowRecord.isFinePaid());

                System.out.println("========================================");
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

    public void searchBorrowRecordsByStudentId(int studentId) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            String jpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.student.studentId = :studentId
                ORDER BY br.issueDate DESC
                """;

            Query<BorrowRecord> query =
                    session.createQuery(
                            jpql,
                            BorrowRecord.class
                    );

            query.setParameter("studentId", studentId);

            List<BorrowRecord> borrowRecords =
                    query.getResultList();

            if (borrowRecords.isEmpty()) {

                System.out.println("No records found for this student.");
                return;

            }

            for (BorrowRecord borrowRecord : borrowRecords) {

                System.out.println("========================================");
                System.out.println("Borrow ID      : "
                        + borrowRecord.getBorrowId());

                System.out.println("Student ID     : "
                        + borrowRecord.getStudent().getStudentId());

                System.out.println("Student Name   : "
                        + borrowRecord.getStudent().getName());

                System.out.println("Book ID        : "
                        + borrowRecord.getBook().getBookId());

                System.out.println("Book Title     : "
                        + borrowRecord.getBook().getTitle());

                System.out.println("Issue Date     : "
                        + borrowRecord.getIssueDate());

                System.out.println("Due Date       : "
                        + borrowRecord.getDueDate());

                System.out.println("Return Date    : "
                        + borrowRecord.getReturnDate());

                System.out.println("Returned       : "
                        + borrowRecord.isReturned());

                System.out.println("Renew Count    : "
                        + borrowRecord.getRenewCount());

                System.out.println("Fine           : Rs. "
                        + borrowRecord.getFine());

                System.out.println("Fine Paid      : "
                        + borrowRecord.isFinePaid());

                System.out.println("========================================");
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

    public void searchBorrowRecordsByBookId(int bookId) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            String jpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.book.bookId = :bookId
                ORDER BY br.issueDate DESC
                """;

            Query<BorrowRecord> query =
                    session.createQuery(
                            jpql,
                            BorrowRecord.class
                    );

            query.setParameter("bookId", bookId);

            List<BorrowRecord> borrowRecords =
                    query.getResultList();

            if (borrowRecords.isEmpty()) {

                System.out.println("No records found for this book.");
                return;

            }

            for (BorrowRecord borrowRecord : borrowRecords) {

                System.out.println("========================================");
                System.out.println("Borrow ID      : "
                        + borrowRecord.getBorrowId());

                System.out.println("Student ID     : "
                        + borrowRecord.getStudent().getStudentId());

                System.out.println("Student Name   : "
                        + borrowRecord.getStudent().getName());

                System.out.println("Book ID        : "
                        + borrowRecord.getBook().getBookId());

                System.out.println("Book Title     : "
                        + borrowRecord.getBook().getTitle());

                System.out.println("Issue Date     : "
                        + borrowRecord.getIssueDate());

                System.out.println("Due Date       : "
                        + borrowRecord.getDueDate());

                System.out.println("Return Date    : "
                        + borrowRecord.getReturnDate());

                System.out.println("Returned       : "
                        + borrowRecord.isReturned());

                System.out.println("Renew Count    : "
                        + borrowRecord.getRenewCount());

                System.out.println("Fine           : Rs. "
                        + borrowRecord.getFine());

                System.out.println("Fine Paid      : "
                        + borrowRecord.isFinePaid());

                System.out.println("========================================");
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

    public void searchBorrowRecordsByStatus(boolean returned) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            String jpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.returned = :returned
                ORDER BY br.issueDate DESC
                """;

            Query<BorrowRecord> query =
                    session.createQuery(
                            jpql,
                            BorrowRecord.class
                    );

            query.setParameter("returned", returned);

            List<BorrowRecord> borrowRecords =
                    query.getResultList();

            if (borrowRecords.isEmpty()) {

                if (returned) {

                    System.out.println("No returned borrow records found.");

                }
                else {

                    System.out.println("No active borrow records found.");

                }

                return;

            }

            for (BorrowRecord borrowRecord : borrowRecords) {

                System.out.println("========================================");
                System.out.println("Borrow ID      : "
                        + borrowRecord.getBorrowId());

                System.out.println("Student ID     : "
                        + borrowRecord.getStudent().getStudentId());

                System.out.println("Student Name   : "
                        + borrowRecord.getStudent().getName());

                System.out.println("Book ID        : "
                        + borrowRecord.getBook().getBookId());

                System.out.println("Book Title     : "
                        + borrowRecord.getBook().getTitle());

                System.out.println("Issue Date     : "
                        + borrowRecord.getIssueDate());

                System.out.println("Due Date       : "
                        + borrowRecord.getDueDate());

                System.out.println("Return Date    : "
                        + borrowRecord.getReturnDate());

                System.out.println("Returned       : "
                        + borrowRecord.isReturned());

                System.out.println("Renew Count    : "
                        + borrowRecord.getRenewCount());

                System.out.println("Fine           : Rs. "
                        + borrowRecord.getFine());

                System.out.println("Fine Paid      : "
                        + borrowRecord.isFinePaid());

                System.out.println("========================================");
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