package org.pras.dto.borrowDtos;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class BorrowedBookResponseDto {

    private int bookId;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date issueDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;

    public BorrowedBookResponseDto() {
    }

    public BorrowedBookResponseDto(
            int bookId,
            String title,
            Date issueDate,
            Date dueDate) {

        this.bookId = bookId;
        this.title = title;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
