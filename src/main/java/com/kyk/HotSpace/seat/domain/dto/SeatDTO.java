package com.kyk.HotSpace.seat.domain.dto;

import com.kyk.HotSpace.reservation.domain.dto.ReservationDTO;
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
    ReservationDTO reservationDTO;
}
