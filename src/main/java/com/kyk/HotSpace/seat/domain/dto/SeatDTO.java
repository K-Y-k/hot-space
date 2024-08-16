package com.kyk.HotSpace.seat.domain.dto;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.seat.domain.entity.Seat;
import com.kyk.HotSpace.store.domain.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatDTO {
    private Long seatId;
    private String seatType;
    private double posX;
    private double posY;
    private boolean available;
    private int tableCapacity;
}
