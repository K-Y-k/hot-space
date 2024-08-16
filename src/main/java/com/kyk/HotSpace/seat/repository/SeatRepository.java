package com.kyk.HotSpace.seat.repository;

import com.kyk.HotSpace.seat.domain.entity.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatRepository {
    Seat saveSeat(Seat seat);
    Optional<Seat> findById(Long id);
    Optional<Seat> findFirstByStoreId(Long storeId);
    List<Seat> findByStoreId(Long storeId);
    void deleteAllByStoreId(Long storeId);

    void clear();
}
