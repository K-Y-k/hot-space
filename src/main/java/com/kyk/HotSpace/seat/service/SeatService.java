package com.kyk.HotSpace.seat.service;

import com.kyk.HotSpace.seat.domain.dto.SeatDTO;

public interface SeatService {
    Long saveSeat(SeatDTO seatDTO, Long storeId);
}
