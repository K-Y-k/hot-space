package com.kyk.HotSpace.reservation.service;

import com.kyk.HotSpace.reservation.domain.dto.ReservationUploadForm;
import com.kyk.HotSpace.reservation.domain.entity.ApprovalState;
import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ReservationService {
    void saveReservation(ReservationUploadForm form, LocalDateTime convertDateTime, Long memberId, Long storeId);
    void changeApprovalState(Long reservationId, ApprovalState approvalState);
    void deleteReservation(Long reservationId);

    Long findSeatId(Long reservationId);
    void cancelReservation(Long seatId);

    Page<Reservation> findReservationsByMemberId(Long memberId, Pageable pageable);
    Page<Reservation> findByStoreIdAndApprovalState(Pageable pageable, Long storeId, ApprovalState approvalState);
}
