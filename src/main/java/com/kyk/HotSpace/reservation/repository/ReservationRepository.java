package com.kyk.HotSpace.reservation.repository;

import com.kyk.HotSpace.reservation.domain.entity.Reservation;

import java.util.Optional;


public interface ReservationRepository {
    Reservation saveReservation(Reservation reservation);
    Optional<Reservation> findById(Long reservationId);
    void delete(Long reservationId);
    void clear();
}
