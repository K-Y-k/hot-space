package com.kyk.HotSpace.reservation.domain.dto;


import com.kyk.HotSpace.reservation.domain.entity.ApprovalState;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservationDTO {
    private Long seatId;
    private String name;
    private String phoneNum;
    private LocalDateTime dateTime;
    private int guestCount;
    private ApprovalState approvalState;
}
