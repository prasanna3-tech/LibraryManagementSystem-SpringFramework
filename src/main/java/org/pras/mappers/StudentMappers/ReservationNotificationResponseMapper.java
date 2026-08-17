package org.pras.mappers.StudentMappers;

import org.pras.dto.studentDtos.ReservationNotificationResponseDto;
import org.pras.models.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationNotificationResponseMapper {

    public ReservationNotificationResponseDto toResponseDto(
            Reservation reservation) {

        ReservationNotificationResponseDto dto =
                new ReservationNotificationResponseDto();

        dto.setBookId(
                reservation.getBook().getBookId()
        );

        dto.setTitle(
                reservation.getBook().getTitle()
        );

        dto.setReservationDate(
                reservation.getReservationDate()
        );

        return dto;
    }
}