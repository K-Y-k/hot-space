package com.kyk.HotSpace.seat.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatStatistics {
    private int totalCount;
    private int usingCount;
    private int remainingCount;
}
