package org.pras.dto.studentDtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class ReservationNotificationResponseDto {

    private int bookId;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date reservationDate;

    public ReservationNotificationResponseDto() {
    }

    public ReservationNotificationResponseDto(
            int bookId,
            String title,
            Date reservationDate) {

        this.bookId = bookId;
        this.title = title;
        this.reservationDate = reservationDate;
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

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }
}