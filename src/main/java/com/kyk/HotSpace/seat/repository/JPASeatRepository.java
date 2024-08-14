package com.kyk.HotSpace.seat.repository;

import com.kyk.HotSpace.seat.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


interface JPASeatRepository extends JpaRepository<Seat, Long> {
}
