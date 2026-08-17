package org.pras.dto.borrowDtos;

import java.sql.Date;

public class RenewBookRequestDto {

    private int studentId;
    private int bookId;
    private Date newDueDate;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getNewDueDate() {
        return newDueDate;
    }

    public void setNewDueDate(Date newDueDate) {
        this.newDueDate = newDueDate;
    }
}