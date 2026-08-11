package org.pras.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(name = "reservation_id")
    private int reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "reservation_date", nullable = false)
    private Date reservationDate;

    @Column(nullable = false)
    private boolean notified = false;

    public Reservation() {

    }

    public Reservation(int reservationId,
                       Student student,
                       Book book,
                       Date reservationDate) {

        this.reservationId = reservationId;
        this.student = student;
        this.book = book;
        this.reservationDate = reservationDate;
        this.notified = false;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public void displayReservationDetails() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Book ID: " + book.getBookId());
        System.out.println("Reservation Date: " + reservationDate);
        System.out.println("Notified: " + notified);
    }
}