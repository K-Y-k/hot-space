package com.kyk.HotSpace.reservation.repository;

import com.kyk.HotSpace.reservation.domain.entity.ApprovalState;
import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringDataJpaReservationRepository implements ReservationRepository {

    private final JPAReservationRepository JPAReservationRepository;

    @Override
    public Reservation saveReservation(Reservation reservation) {
        return JPAReservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return JPAReservationRepository.findById(reservationId);
    }

    @Override
    public Optional<Reservation> findBySeatIdAndApprovalState(Long seatId, ApprovalState approvalState) {
        return JPAReservationRepository.findBySeatIdAndApprovalState(seatId, approvalState);
    }

    @Override
    public Page<Reservation> findReservationsByMemberId(Long memberId, Pageable pageable) {
        return JPAReservationRepository.findReservationsByMemberId(memberId, pageable);
    }

    @Override
    public Page<Reservation> findByStoreIdAndApprovalState(Pageable pageable, Long storeId, ApprovalState approvalState) {
        return JPAReservationRepository.findByStoreIdAndApprovalState(pageable, storeId, approvalState);
    }

    @Override
    public void delete(Long reservationId) {
        JPAReservationRepository.deleteById(reservationId);
    }

    @Override
    public void clear() {
        JPAReservationRepository.deleteAll();
    }
}