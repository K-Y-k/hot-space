package com.kyk.HotSpace.reservation.repository;

import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAReservationRepository extends JpaRepository<Reservation, Long> {
}
