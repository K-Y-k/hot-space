package com.kyk.HotSpace.seat.repository;

import com.kyk.HotSpace.seat.domain.entity.Seat;

import java.util.Optional;


public interface SeatRepository {
    Seat saveSeat(Seat seat);
    void clear();
}
