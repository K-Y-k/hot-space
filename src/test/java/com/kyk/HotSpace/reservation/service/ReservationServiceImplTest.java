package com.kyk.HotSpace.reservation.service;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.domain.entity.Role;
import com.kyk.HotSpace.member.repository.MemberRepository;
import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import com.kyk.HotSpace.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class ReservationServiceImplTest {
    @Autowired MemberRepository memberRepository;
    @Autowired ReservationRepository reservationRepository;

    @AfterEach
    void afterEach() {
        reservationRepository.clear();
    }

    @Test
    @DisplayName("예약 저장")
    void saveReservation() {
        // given
        Reservation reservation = new Reservation(null, "김용경", "010-1020-4949", LocalDateTime.now(), 3, false, null, null, null);

        // when
        Reservation savedReservation = reservationRepository.saveReservation(reservation);

        // then
        assertThat(savedReservation.getName()).isEqualTo(reservation.getName());
    }

    @Test
    @DisplayName("예약 취소")
    void deleteReservation() {
        // given
        Reservation reservation = new Reservation(null, "김용경", "010-1020-4949", LocalDateTime.now(), 3, false, null, null, null);
        Reservation savedReservation = reservationRepository.saveReservation(reservation);

        // when
        reservationRepository.delete(savedReservation.getId());

        // then
        assertTrue(reservationRepository.findById(savedReservation.getId()).isEmpty());
    }

    @Test
    @DisplayName("예약 리스트")
    void storeList() {
        // given
        Member member = new Member("memberA", "ID123", "pass123", Role.CUSTOMER);
        Member savedMember = memberRepository.saveMember(member);

        Reservation reservation1 = new Reservation(null, "김용경", "010-1020-4949", LocalDateTime.now(), 3, false, savedMember, null, null);
        Reservation reservation2 = new Reservation(null, "김용경", "010-1020-4949", LocalDateTime.now(), 3, false, savedMember, null, null);
        Reservation reservation3 = new Reservation(null, "김용경", "010-1020-4949", LocalDateTime.now(), 3, false, savedMember, null, null);
        reservationRepository.saveReservation(reservation1);
        reservationRepository.saveReservation(reservation2);
        reservationRepository.saveReservation(reservation3);

        // when
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Reservation> pageResult = reservationRepository.findReservationsByMemberId(savedMember.getId(), pageable);

        // then
        assertEquals(10, pageResult.getSize());          // 한 페이지의 최대 데이터 개수
        assertEquals(3, pageResult.getContent().size()); // 한 페이지의 최대 데이터 개수 기준으로 포함된 총 데이터 크기
        assertEquals(3, pageResult.getTotalElements());  // 전체 총 데이터 크기
        assertEquals(1, pageResult.getTotalPages());     // 현재 총 페이지 수
        assertEquals(0, pageResult.getNumber());         // 현재 페이지 번호
    }
}
