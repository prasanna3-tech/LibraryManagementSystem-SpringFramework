package org.pras.mappers.borrowRecordMappers;

import org.pras.dto.borrowDtos.ReservationResponseDto;
import org.pras.models.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationResponseMapper {

    public ReservationResponseDto toResponseDto(
            Reservation reservation) {

        ReservationResponseDto dto =
                new ReservationResponseDto();

        dto.setReservationId(
                reservation.getReservationId()
        );

        dto.setStudentId(
                reservation.getStudent().getStudentId()
        );

        dto.setBookId(
                reservation.getBook().getBookId()
        );

        dto.setReservationDate(
                reservation.getReservationDate()
        );

        dto.setNotified(
                reservation.isNotified()
        );

        return dto;
    }
}