package com.kyk.HotSpace.reservation.domain.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationUploadForm {
    @Size(min = 2, max = 7, message = "최소 2글자, 최대 7글자입니다.")
    private String name;

    @Size(min = 6, max = 10, message = "다시 입력해주세요")
    private String phoneNum;

    @NotNull(message = "예약 날짜를 입력해 주세요.")
    @FutureOrPresent(message = "예약 날짜는 현재 이후로 정해주세요")
    private LocalDate date;

    @NotNull(message = "예약 시간을 입력해 주세요.")
    private LocalTime time;

    @NotNull(message = "참석 인원을 입력해 주세요.")
    @Min(value = 1, message = "참석 인원은 1명 이상이어야 합니다.")
    private int guestCount;
}
