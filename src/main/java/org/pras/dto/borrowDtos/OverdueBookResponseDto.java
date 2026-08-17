package org.pras.dto.borrowDtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class OverdueBookResponseDto {

    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;
    private int lateDays;
    private double currentFine;

    public OverdueBookResponseDto() {
    }

    public OverdueBookResponseDto(
            String title,
            Date dueDate,
            int lateDays,
            double currentFine) {

        this.title = title;
        this.dueDate = dueDate;
        this.lateDays = lateDays;
        this.currentFine = currentFine;
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

    public int getLateDays() {
        return lateDays;
    }

    public void setLateDays(int lateDays) {
        this.lateDays = lateDays;
    }

    public double getCurrentFine() {
        return currentFine;
    }

    public void setCurrentFine(double currentFine) {
        this.currentFine = currentFine;
    }
}