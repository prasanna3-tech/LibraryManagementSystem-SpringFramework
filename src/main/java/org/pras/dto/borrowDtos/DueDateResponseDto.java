package org.pras.dto.borrowDtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class DueDateResponseDto {

    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;

    public DueDateResponseDto() {
    }

    public DueDateResponseDto(
            String title,
            Date dueDate) {

        this.title = title;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
