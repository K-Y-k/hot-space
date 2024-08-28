package com.kyk.HotSpace.reservation.service;

import com.kyk.HotSpace.reservation.domain.dto.ReservationUploadForm;
import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import com.kyk.HotSpace.reservation.repository.ReservationRepository;
import com.kyk.HotSpace.seat.domain.dto.SeatDTO;
import com.kyk.HotSpace.seat.domain.entity.Seat;
import com.kyk.HotSpace.seat.repository.SeatRepository;
import com.kyk.HotSpace.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;


    // DTO -> 엔티티 변환 메서드
    public Reservation toReservationEntity(ReservationUploadForm form, LocalDateTime convertDateTime, Seat seat) {
        return Reservation.builder()
                .name(form.getName())
                .phoneNum(form.getPhoneNum())
                .dateTime(convertDateTime)
                .guestCount(form.getGuestCount())
                .seat(seat)
                .build();
    }

    @Override
    public void saveReservation(ReservationUploadForm form, LocalDateTime convertDateTime) {
        Seat findSeat = seatRepository.findById(form.getSeatId()).orElseThrow(() ->
                new IllegalArgumentException("테이블 가져오기 실패: 테이블을 찾지 못했습니다." + form.getSeatId()));

        Reservation reservationEntity = toReservationEntity(form, convertDateTime, findSeat);

        reservationRepository.saveReservation(reservationEntity);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        reservationRepository.delete(reservationId);
    }
}
