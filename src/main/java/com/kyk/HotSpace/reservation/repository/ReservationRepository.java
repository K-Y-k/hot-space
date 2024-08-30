package com.kyk.HotSpace.reservation.repository;

import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface ReservationRepository {
    Reservation saveReservation(Reservation reservation);
    Optional<Reservation> findById(Long reservationId);
    List<Reservation> findByStore_Id(Long storeId);
    Page<Reservation> findReservationsByMemberId(Long memberId, Pageable pageable);
    void delete(Long reservationId);
    void clear();
}
