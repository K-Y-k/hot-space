package com.kyk.HotSpace.reservation.service;

import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import com.kyk.HotSpace.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class ReservationServiceImplTest {
    @Autowired ReservationRepository reservationRepository;

    @AfterEach
    void afterEach() {
        reservationRepository.clear();
    }

    @Test
    @DisplayName("예약 저장")
    void saveReservation() {
        // given
        Reservation reservation = new Reservation(null, "김용경", "010-1020-4949", LocalDateTime.now(), 3, null);

        // when
        Reservation savedReservation = reservationRepository.saveReservation(reservation);

        // then
        assertThat(savedReservation.getName()).isEqualTo(reservation.getName());
    }

    @Test
    @DisplayName("예약 취소")
    void deleteReservation() {
        // given
        Reservation reservation = new Reservation(null, "김용경", "010-1020-4949", LocalDateTime.now(), 3, null);
        Reservation savedReservation = reservationRepository.saveReservation(reservation);

        // when
        reservationRepository.delete(savedReservation.getId());

        // then
        assertTrue(reservationRepository.findById(savedReservation.getId()).isEmpty());
    }

}
