package com.kyk.HotSpace.seat.service;

import com.kyk.HotSpace.seat.domain.dto.SeatDTO;
import com.kyk.HotSpace.seat.domain.entity.Seat;

import java.util.List;

public interface SeatService {
    Long saveSeat(SeatDTO seatDTO, Long storeId);
    SeatDTO changeAvailable(Long seatId);
    List<Seat> findSeatsByStoreId(Long storeId);
    void deleteAllByStoreId(Long storeId);
}
