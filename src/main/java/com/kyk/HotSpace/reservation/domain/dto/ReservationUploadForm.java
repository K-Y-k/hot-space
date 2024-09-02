package com.kyk.HotSpace.reservation.domain.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationUploadForm {
    private Long seatId;

    @Size(min = 2, max = 7, message = "최소 2글자, 최대 7글자입니다.")
    private String name;

    @Size(min = 6, max = 10, message = "다시 입력해주세요")
    private String phoneNum;

    @NotNull(message = "예약 일시를 입력해 주세요.")
    private String dateTime;

    @Min(value = 1, message = "참석 인원은 1명 이상이어야 합니다.")
    private int guestCount;
}
