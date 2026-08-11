package org.pras.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pras.config.HibernateUtil;
import org.pras.models.*;
import org.pras.utils.*;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class BorrowService {

    public BorrowService() {
    }
    public void issueBook(int studentId,
                          int bookId,
                          Date issueDate,
                          Date dueDate) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {

            //STUDENT CHECK AND FINE CALCULATION

            Student student = session.find(Student.class, studentId);

            if (student == null) {
                System.out.println("Student not found");
                return;
            }

            //BOOK CHECK AND QUANTITY
            System.out.println(bookId);
            Book book = session.find(Book.class, bookId);

            if (book == null) {
                System.out.println("Book not found");
                return;
            }
            if (book.getQuantity() <= 0) {
                System.out.println("Book is not available");
                return;
            }


            String jpql1 = """
        SELECT br
        FROM BorrowRecord br
        WHERE br.student.studentId = :studentId
        AND br.book.bookId = :bookId
        AND br.returned = false
        """;

            Query<BorrowRecord> query1 =
                    session.createQuery(jpql1, BorrowRecord.class);

            query1.setParameter("studentId", studentId);
            query1.setParameter("bookId", bookId);

            query1.setMaxResults(1);

            BorrowRecord borrowRecord = query1.uniqueResult();

            if (borrowRecord != null) {
                System.out.println("Student already borrowed this book");
                return;
            }
            //RESERVATION CHECK
            String jpql = """
        SELECT r
        FROM Reservation r
        WHERE r.book.bookId = :bookId
        """;

            Query<Reservation> query =
                    session.createQuery(jpql, Reservation.class);

            query.setParameter("bookId", bookId);

            query.setMaxResults(1);

            Reservation reservation = query.uniqueResult();

            if (reservation != null &&
                    reservation.getStudent().getStudentId() != studentId && book.getQuantity()==1) {

                System.out.println(
                        "This book is reserved by another student."
                );

                return;
            }



            String jpql2 = """
        SELECT COUNT(br)
        FROM BorrowRecord br
        WHERE br.student.studentId = :studentId
        AND br.returned = false
        """;

            Query<Long> query2 =
                    session.createQuery(jpql2, Long.class);

            query2.setParameter("studentId", studentId);

            Long borrowedCount = query2.uniqueResult();

            SystemSettings systemSettings =
                    session.find(SystemSettings.class, 1);

            if (systemSettings == null) {
                System.out.println("System settings not found.");
                return;
            }

            int maxBooksAllowed =
                    systemSettings.getMaxBooksAllowed();

            if (borrowedCount >= maxBooksAllowed) {
                System.out.println("Student reached maximum borrow limit");
                return;
            }
            if (student.getFineAmount() > 0) {
                System.out.println("Student has pending fine");
                return;
            }

            transaction = session.beginTransaction();

// Update Book Quantity (Dirty Checking)

            book.setQuantity(book.getQuantity() - 1);

// Create Borrow Record

            BorrowRecord borrowRecord1 = new BorrowRecord();

            borrowRecord1.setStudent(student);
            borrowRecord1.setBook(book);
            borrowRecord1.setIssueDate(issueDate);
            borrowRecord1.setDueDate(dueDate);

            session.persist(borrowRecord1);

// Delete Reservation (if any)

            String jpql3 = """
        SELECT r
        FROM Reservation r
        WHERE r.student.studentId = :studentId
        AND r.book.bookId = :bookId
        """;

            Query<Reservation> query3 =
                    session.createQuery(jpql3, Reservation.class);

            query3.setParameter("studentId", studentId);
            query3.setParameter("bookId", bookId);

            query3.setMaxResults(1);

            Reservation reservation1 = query3.uniqueResult();

            if (reservation1 != null) {
                session.remove(reservation1);
            }

// Commit Transaction

            transaction.commit();

            System.out.println("Book issued successfully");

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();

        } finally {

            session.close();

        }
    }

    public void displayBorrowedBooks(int studentId) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            String jpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.student.studentId = :studentId
                AND br.returned = false
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

                System.out.println("No borrowed books found.");
                return;

            }

            for (BorrowRecord borrowRecord : borrowRecords) {

                System.out.println("----------------------------");
                System.out.println("Book ID    : "
                        + borrowRecord.getBook().getBookId());
                System.out.println("Book       : "
                        + borrowRecord.getBook().getTitle());
                System.out.println("Issue Date : "
                        + borrowRecord.getIssueDate());
                System.out.println("Due Date   : "
                        + borrowRecord.getDueDate());

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }
        finally {

            session.close();

        }

    }

    public void displayDueDates(int studentId) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            String jpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.student.studentId = :studentId
                AND br.returned = false
                ORDER BY br.dueDate
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

                System.out.println("No borrowed books found.");
                return;

            }

            for (BorrowRecord borrowRecord : borrowRecords) {

                System.out.println("----------------------------");
                System.out.println("Book     : "
                        + borrowRecord.getBook().getTitle());
                System.out.println("Due Date : "
                        + borrowRecord.getDueDate());

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }
        finally {

            session.close();

        }

    }

    public void displayOverdueBooks(int studentId, Date todayDate) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        try {

            // Get Fine Per Day

            SystemSettings settings =
                    session.find(SystemSettings.class, 1);

            if (settings == null) {

                System.out.println("System settings not found.");
                return;

            }

            double finePerDay = settings.getFinePerDay();

            // Get Borrowed Books

            String jpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.student.studentId = :studentId
                AND br.returned = false
                ORDER BY br.dueDate
                """;

            Query<BorrowRecord> query =
                    session.createQuery(
                            jpql,
                            BorrowRecord.class
                    );

            query.setParameter("studentId", studentId);

            List<BorrowRecord> borrowRecords =
                    query.getResultList();

            boolean found = false;

            for (BorrowRecord borrowRecord : borrowRecords) {

                Date dueDate = borrowRecord.getDueDate();

                int lateDays =
                        DateUtilNew.getLateDays(
                                dueDate,
                                todayDate
                        );

                if (lateDays > 0) {

                    found = true;

                    double fine = lateDays * finePerDay;

                    System.out.println("------------------------------------");
                    System.out.println("Book Title   : "
                            + borrowRecord.getBook().getTitle());
                    System.out.println("Due Date     : "
                            + dueDate);
                    System.out.println("Late Days    : "
                            + lateDays);
                    System.out.println("Current Fine : Rs. "
                            + fine);

                }

            }

            if (!found) {

                System.out.println("No overdue books.");

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }
        finally {

            session.close();

        }

    }

    public void payFine(int studentId) {

        Scanner sc = new Scanner(System.in);

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            // 1. Calculate total unpaid fine

            String sumJpql = """
                SELECT SUM(br.fine)
                FROM BorrowRecord br
                WHERE br.student.studentId = :studentId
                AND br.returned = true
                AND br.finePaid = false
                """;

            Query<Double> sumQuery =
                    session.createQuery(sumJpql, Double.class);

            sumQuery.setParameter("studentId", studentId);

            Double totalFine = sumQuery.uniqueResult();

            if (totalFine == null) {
                totalFine = 0.0;
            }

            if (totalFine <= 0) {

                System.out.println("No pending fine.");
                return;

            }

            System.out.println("Total Pending Fine: Rs. " + totalFine);

            // 2. Get payment amount

            while (true) {

                System.out.print("Enter amount to pay: ");

                double amount = sc.nextDouble();
                sc.nextLine();

                if (amount == totalFine) {
                    break;
                }

                System.out.println(
                        "Please pay exactly Rs. " + totalFine
                );
            }

            // 3. Start Transaction

            transaction = session.beginTransaction();

            // 4. Fetch all unpaid borrow records

            String borrowJpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.student.studentId = :studentId
                AND br.returned = true
                AND br.finePaid = false
                """;

            Query<BorrowRecord> borrowQuery =
                    session.createQuery(
                            borrowJpql,
                            BorrowRecord.class
                    );

            borrowQuery.setParameter("studentId", studentId);

            List<BorrowRecord> borrowRecords =
                    borrowQuery.getResultList();

            // 5. Mark every fine as paid

            for (BorrowRecord borrowRecord : borrowRecords) {

                borrowRecord.setFinePaid(true);

            }

            // 6. Commit

            transaction.commit();

            System.out.println("Payment successful.");
            System.out.println("Amount Paid: Rs. " + totalFine);
            System.out.println("Remaining Fine: Rs. 0.0");

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

    public void displayBorrowHistory(int studentId) {

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

                System.out.println("No borrow history found.");
                return;

            }

            for (BorrowRecord borrowRecord : borrowRecords) {

                System.out.println("--------------------------------------");
                System.out.println("Book         : "
                        + borrowRecord.getBook().getTitle());
                System.out.println("Issue Date   : "
                        + borrowRecord.getIssueDate());
                System.out.println("Due Date     : "
                        + borrowRecord.getDueDate());
                System.out.println("Return Date  : "
                        + borrowRecord.getReturnDate());
                System.out.println("Returned     : "
                        + borrowRecord.isReturned());
                System.out.println("Renew Count  : "
                        + borrowRecord.getRenewCount());
                System.out.println("Fine         : Rs. "
                        + borrowRecord.getFine());
                System.out.println("Fine Paid    : "
                        + borrowRecord.isFinePaid());

            }

        }
        catch (Exception e) {

            e.printStackTrace();

        }
        finally {

            session.close();

        }

    }

    public void returnBook(int studentId, int bookId, Date returnDate) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            // Find Active Borrow Record

            String jpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.student.studentId = :studentId
                AND br.book.bookId = :bookId
                AND br.returned = false
                """;

            Query<BorrowRecord> query =
                    session.createQuery(jpql, BorrowRecord.class);

            query.setParameter("studentId", studentId);
            query.setParameter("bookId", bookId);

            query.setMaxResults(1);

            BorrowRecord borrowRecord =
                    query.uniqueResult();

            if (borrowRecord == null) {

                System.out.println("Borrow record not found");
                return;

            }

            Date dueDate = borrowRecord.getDueDate();

            // System Settings

            SystemSettings systemSettings =
                    session.find(SystemSettings.class, 1);

            if (systemSettings == null) {

                System.out.println("System settings not found.");
                return;

            }

            double finePerDay =
                    systemSettings.getFinePerDay();

            // Fine Calculation

            int lateDays =
                    DateUtilNew.getLateDays(dueDate, returnDate);

            double fine =
                    lateDays > 0
                            ? lateDays * finePerDay
                            : 0;

            // Transaction Starts

            transaction = session.beginTransaction();

            // Update Borrow Record

            borrowRecord.setReturned(true);
            borrowRecord.setReturnDate(returnDate);
            borrowRecord.setFine(fine);
            if(fine==0){
                borrowRecord.setFinePaid(true);
            }else {
                borrowRecord.setFinePaid(false);
            }

            // Increase Book Quantity

            Book book = borrowRecord.getBook();

            book.setQuantity(book.getQuantity() + 1);

            // Notify Reservation (if any)

            String reservationJpql = """
                SELECT r
                FROM Reservation r
                WHERE r.book.bookId = :bookId
                """;

            Query<Reservation> reservationQuery =
                    session.createQuery(
                            reservationJpql,
                            Reservation.class
                    );

            reservationQuery.setParameter("bookId", bookId);

            reservationQuery.setMaxResults(1);

            Reservation reservation =
                    reservationQuery.uniqueResult();

            if (reservation != null) {

                reservation.setNotified(true);

            }

            // Commit

            transaction.commit();

            System.out.println("Book returned successfully");

            if (fine > 0) {

                System.out.println("Late Days : " + lateDays);
                System.out.println("Fine : Rs. " + fine);

            }

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

            }

            e.printStackTrace();

        } finally {

            session.close();

        }

    }

    public void renewBook(int studentId, int bookId, Date newDueDate) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            // Check Borrow Record

            String borrowJpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.student.studentId = :studentId
                AND br.book.bookId = :bookId
                AND br.returned = false
                """;

            Query<BorrowRecord> borrowQuery =
                    session.createQuery(
                            borrowJpql,
                            BorrowRecord.class
                    );

            borrowQuery.setParameter("studentId", studentId);
            borrowQuery.setParameter("bookId", bookId);
            borrowQuery.setMaxResults(1);

            BorrowRecord borrowRecord =
                    borrowQuery.uniqueResult();

            if (borrowRecord == null) {

                System.out.println("Borrow record not found");
                return;

            }

            // Check Renew Limit

            SystemSettings settings =
                    session.find(SystemSettings.class, 1);

            if (settings == null) {

                System.out.println("System settings not found.");
                return;

            }

            if (borrowRecord.getRenewCount() >=
                    settings.getMaxRenewCount()) {

                System.out.println("Maximum renew limit reached");
                return;

            }

            // Check Reservation

            String reservationJpql = """
                SELECT r
                FROM Reservation r
                WHERE r.book.bookId = :bookId
                """;

            Query<Reservation> reservationQuery =
                    session.createQuery(
                            reservationJpql,
                            Reservation.class
                    );

            reservationQuery.setParameter("bookId", bookId);
            reservationQuery.setMaxResults(1);

            Reservation reservation =
                    reservationQuery.uniqueResult();

            if (reservation != null) {

                System.out.println(
                        "Cannot renew because another student reserved this book"
                );

                return;

            }

            // Begin Transaction

            transaction = session.beginTransaction();

            // Renew Book

            borrowRecord.setDueDate(newDueDate);

            borrowRecord.setRenewCount(
                    borrowRecord.getRenewCount() + 1
            );

            // Commit

            transaction.commit();

            System.out.println("Book renewed successfully");
            System.out.println("New Due Date: " + newDueDate);
            System.out.println(
                    "Renew Count: " + borrowRecord.getRenewCount()
            );

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

    public void reserveBook(int studentId, int bookId, Date reservationDate) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            // Step 1: Check Book

            Book book = session.find(Book.class, bookId);

            if (book == null) {

                System.out.println("Book not found");
                return;

            }
            if(book.getQuantity()==1){
                String reservationJpql = """
                SELECT r
                FROM Reservation r
                WHERE r.book.bookId = :bookId
                """;

                Query<Reservation> reservationQuery =
                        session.createQuery(
                                reservationJpql,
                                Reservation.class
                        );

                reservationQuery.setParameter("bookId", bookId);
                reservationQuery.setMaxResults(1);

                Reservation existingReservation =
                        reservationQuery.uniqueResult();

                if (existingReservation != null) {

                    System.out.println("This book is already reserved.");
                    return;

                }
            }

            if (book.getQuantity() > 0) {

                System.out.println("Book is already available. No need to reserve.");
                return;

            }

            // Step 2: Check if student already borrowed this book

            String borrowJpql = """
                SELECT br
                FROM BorrowRecord br
                WHERE br.student.studentId = :studentId
                AND br.book.bookId = :bookId
                AND br.returned = false
                """;

            Query<BorrowRecord> borrowQuery =
                    session.createQuery(
                            borrowJpql,
                            BorrowRecord.class
                    );

            borrowQuery.setParameter("studentId", studentId);
            borrowQuery.setParameter("bookId", bookId);
            borrowQuery.setMaxResults(1);

            BorrowRecord borrowRecord =
                    borrowQuery.uniqueResult();

            if (borrowRecord != null) {

                System.out.println("Student already borrowed this book");
                return;

            }

            // Step 3: Check duplicate reservation

            String reservationJpql = """
        SELECT r
        FROM Reservation r
        WHERE r.book.bookId = :bookId
        """;

            Query<Reservation> reservationQuery =
                    session.createQuery(
                            reservationJpql,
                            Reservation.class
                    );

            reservationQuery.setParameter("bookId", bookId);
            reservationQuery.setMaxResults(1);

            Reservation reservation = reservationQuery.uniqueResult();

            if (reservation != null) {

                if (reservation.getStudent().getStudentId() == studentId) {

                    System.out.println("Student already reserved this book");

                } else {

                    System.out.println("This book is already reserved by another student");

                }

                return;

            }

            // Step 4: Check Student

            Student student =
                    session.find(Student.class, studentId);

            if (student == null) {

                System.out.println("Student not found");
                return;

            }

            // Step 5: Begin Transaction

            transaction = session.beginTransaction();

            // Step 6: Create Reservation

            Reservation reservation1 = new Reservation();

            reservation1.setStudent(student);
            reservation1.setBook(book);
            reservation1.setReservationDate(reservationDate);
            reservation1.setNotified(false);

            session.persist(reservation1);

            // Step 7: Commit

            transaction.commit();

            System.out.println("Book reserved successfully");

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