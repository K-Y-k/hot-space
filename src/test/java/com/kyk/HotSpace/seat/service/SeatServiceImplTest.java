package com.kyk.HotSpace.seat.service;

import com.kyk.HotSpace.seat.domain.entity.Seat;
import com.kyk.HotSpace.seat.repository.SeatRepository;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.repository.StoreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class SeatServiceImplTest {
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    SeatRepository seatRepository;

    private Store savedStore;

    @BeforeEach
    void setUp() {
        // 공통으로 사용하는 Store 객체를 생성하고 저장
        Store store = new Store(null, "편의점", "ec편의점", "유성구~~대로", "010-0200-0200", "www.dasd.com", 40.2, 33.5, null, null, null);
        savedStore = storeRepository.save(store);
    }

    @AfterEach
    void afterEach() {
        storeRepository.clear();
        seatRepository.clear();
    }


    @Test
    @DisplayName("등록한 가게에 배치한 좌석 저장")
    void saveSeat() {
        // given
        Seat seat1 = new Seat(null, "small", 1, 214.2, 531.3, true, savedStore);

        // when
        Seat savedSeat = seatRepository.saveSeat(seat1);

        // then
        assertThat(savedSeat.getId()).isEqualTo(seat1.getId());
    }

}