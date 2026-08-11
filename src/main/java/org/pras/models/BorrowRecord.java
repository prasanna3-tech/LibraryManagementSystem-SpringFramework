package org.pras.models;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @Column(name = "borrow_record_id")
    private int borrowRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(nullable = false)
    private boolean returned = false;

    @Column(nullable = false)
    private double fine = 0;

    @Column(name = "renew_count", nullable = false)
    private int renewCount = 0;

    @Column(name = "fine_paid", nullable = false)
    private boolean finePaid = false;


    public BorrowRecord() {

    }

    public BorrowRecord(int borrowRecordId,
                        Student student,
                        Book book,
                        Date issueDate,
                        Date dueDate) {

        this.borrowRecordId = borrowRecordId;
        this.student = student;
        this.book = book;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.returned = false;
        this.fine = 0;
        this.renewCount = 0;
        this.finePaid = false;
    }

    public int getBorrowRecordId() {
        return borrowRecordId;
    }

    public void setBorrowRecordId(int borrowRecordId) {
        this.borrowRecordId = borrowRecordId;
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }

    public boolean isFinePaid() {
        return finePaid;
    }

    public void setFinePaid(boolean finePaid) {
        this.finePaid = finePaid;
    }

    public int getBorrowId(){
        return borrowRecordId;
    }

    public void displayBorrowRecord() {
        System.out.println("Borrow Record ID: " + borrowRecordId);
        System.out.println("Student ID: " + student.getStudentId());
        System.out.println("Book ID: " + book.getBookId());
        System.out.println("Issue Date: " + issueDate);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Return Date: " + returnDate);
        System.out.println("Returned: " + returned);
        System.out.println("Fine: Rs. " + fine);
        System.out.println("Renew Count: " + renewCount);
        System.out.println("Fine Paid: " + finePaid);
    }
}