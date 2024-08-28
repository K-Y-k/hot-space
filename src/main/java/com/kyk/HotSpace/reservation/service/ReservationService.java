package com.kyk.HotSpace.reservation.service;

import com.kyk.HotSpace.reservation.domain.dto.ReservationUploadForm;

import java.time.LocalDateTime;

public interface ReservationService {
    void saveReservation(ReservationUploadForm form, LocalDateTime convertDateTime);
    void deleteReservation(Long reservationId);
}
