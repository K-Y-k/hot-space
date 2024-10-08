package com.kyk.HotSpace.seat.service;

import com.kyk.HotSpace.reservation.domain.entity.ApprovalState;
import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import com.kyk.HotSpace.reservation.repository.ReservationRepository;
import com.kyk.HotSpace.seat.domain.dto.SeatDTO;
import com.kyk.HotSpace.seat.domain.dto.SeatStatistics;
import com.kyk.HotSpace.seat.domain.entity.Seat;
import com.kyk.HotSpace.seat.repository.SeatRepository;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SeatServiceImpl implements SeatService {
    private final StoreRepository storeRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;


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

    @Override
    public SeatDTO changeAvailable(Long seatId) {
        Seat findSeat = seatRepository.findById(seatId).orElseThrow(() ->
                new IllegalArgumentException("테이블 가져오기 실패: 테이블을 찾지 못했습니다." + seatId));

        findSeat.changeAvailable();

        return new SeatDTO(findSeat.getId(), findSeat.getSeatType(), findSeat.getPosX(), findSeat.getPosY(), findSeat.isAvailable(), findSeat.getTableCapacity(), null);
    }

    @Override
    public List<Seat> findSeatsByStoreId(Long storeId) {
        return seatRepository.findByStoreId(storeId);
    }

    @Override
    public void deleteAllByStoreId(Long storeId) {
        // 이전에 저장된 좌석이 있는지 검증
        Optional<Seat> findSeat = seatRepository.findFirstByStoreId(storeId);
        if (findSeat.isPresent()) {
            seatRepository.deleteAllByStoreId(storeId);
        }
    }

    @Override
    public SeatStatistics statisticsResult(Long storeId) {
        List<Seat> seats = seatRepository.findByStoreId(storeId);
        Page<Reservation> reservations = reservationRepository.findByStoreIdAndApprovalState(Pageable.unpaged(), storeId, ApprovalState.STAND);

        int totalCount = seats.size();
        int usingCount = seatRepository.countByAvailableFalse();
        int reservationCount = reservations.getSize();
        int remainingCount = totalCount - usingCount - reservationCount;

        return new SeatStatistics(totalCount, usingCount, reservationCount, remainingCount);
    }
}
