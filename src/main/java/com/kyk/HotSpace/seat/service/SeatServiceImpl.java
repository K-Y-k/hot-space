package com.kyk.HotSpace.seat.service;

import com.kyk.HotSpace.seat.domain.dto.SeatDTO;
import com.kyk.HotSpace.seat.domain.entity.Seat;
import com.kyk.HotSpace.seat.repository.SeatRepository;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final StoreRepository storeRepository;
    private final SeatRepository seatRepository;


    // DTO -> 엔티티 변환 메서드
    public Seat toSeatEntity(SeatDTO seatDTO, Store store) {
        return Seat.builder()
                .available(true)
                .seatType(seatDTO.getSeatType())
                .posX(seatDTO.getPosX())
                .posY(seatDTO.getPosY())
                .tableCapacity(seatDTO.getTableCapacity())
                .store(store)
                .build();
    }

    @Override
    public Long saveSeat(SeatDTO seatDTO, Long storeId) {
        Store findStore = storeRepository.findById(storeId).orElseThrow(() ->
                new IllegalArgumentException("가게 가져오기 실패: 가게를 찾지 못했습니다." + storeId));

        Seat seatEntity = toSeatEntity(seatDTO, findStore);

        Seat savedSeat = seatRepository.saveSeat(seatEntity);

        return savedSeat.getId();
    }

}
