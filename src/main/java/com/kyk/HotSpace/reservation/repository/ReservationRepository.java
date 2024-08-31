package com.kyk.HotSpace.reservation.repository;

import com.kyk.HotSpace.reservation.domain.entity.ApprovalState;
import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ReservationRepository {
    Reservation saveReservation(Reservation reservation);
    Optional<Reservation> findById(Long reservationId);
    Optional<Reservation> findBySeatIdAndApprovalState(Long seatId, ApprovalState approvalState);
    Page<Reservation> findReservationsByMemberId(Long memberId, Pageable pageable);
    Page<Reservation> findByStoreIdAndApprovalState(Pageable pageable, Long storeId, ApprovalState approvalState);
    void delete(Long reservationId);
    void clear();
}
