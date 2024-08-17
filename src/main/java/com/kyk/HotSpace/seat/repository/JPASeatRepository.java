package com.kyk.HotSpace.seat.repository;

import com.kyk.HotSpace.seat.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface JPASeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findFirstByStoreId(Long storeId);
    List<Seat> findByStoreId(Long storeId);
    void deleteAllByStoreId(Long storeId);
    int countByAvailableFalse();
}
