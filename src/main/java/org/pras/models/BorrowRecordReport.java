package org.pras.models;

import java.sql.Date;

public class BorrowRecordReport {

    private int borrowId;

    private int studentId;
    private String studentName;

    private int bookId;
    private String bookTitle;

    private Date issueDate;
    private Date dueDate;
    private Date returnDate;

    private boolean returned;

    private int renewCount;

    private double fine;

    private boolean finePaid;

    public BorrowRecordReport() {

    }

    public BorrowRecordReport(
            int borrowId,
            int studentId,
            String studentName,
            int bookId,
            String bookTitle,
            Date issueDate,
            Date dueDate,
            Date returnDate,
            boolean returned,
            int renewCount,
            double fine,
            boolean finePaid) {

        this.borrowId = borrowId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.returned = returned;
        this.renewCount = renewCount;
        this.fine = fine;
        this.finePaid = finePaid;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    public boolean isFinePaid() {
        return finePaid;
    }

    public void setFinePaid(boolean finePaid) {
        this.finePaid = finePaid;
    }
}