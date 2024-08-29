package com.kyk.HotSpace.reservation.repository;

import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findReservationsByMemberId(Long memberId, Pageable pageable);
}