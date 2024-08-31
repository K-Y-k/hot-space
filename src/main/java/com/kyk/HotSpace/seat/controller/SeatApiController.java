package com.kyk.HotSpace.seat.controller;

import com.kyk.HotSpace.reservation.domain.dto.ReservationDTO;
import com.kyk.HotSpace.reservation.service.ReservationService;
import com.kyk.HotSpace.seat.domain.dto.SeatDTO;
import com.kyk.HotSpace.seat.domain.entity.Seat;
import com.kyk.HotSpace.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatApiController {
    private final SeatService seatService;
    private final ReservationService reservationService;

    @PostMapping("/api/upload/{storeId}")
    public ResponseEntity<?> seatUpload(@RequestBody List<SeatDTO> seatDTOs, @PathVariable Long storeId) {
        // 이전 좌석 데이터들 모두 삭제
        seatService.deleteAllByStoreId(storeId);

        log.info("가게 Id = {}", storeId);
        
        for (SeatDTO seatDTO : seatDTOs) {
            log.info("전송 좌석 정보 = {}", seatDTO.getSeatType());
            log.info("= {}", seatDTO.getTableCapacity());
            log.info("= {}", seatDTO.getPosX());
            log.info("= {}", seatDTO.getPosY());

            seatService.saveSeat(seatDTO, storeId);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/api/seatList/{storeId}")
    public ResponseEntity<?> getSeatsByStoreId(@PathVariable Long storeId) {
        List<Seat> seats = seatService.findSeatsByStoreId(storeId);

        List<SeatDTO> seatDTOs = seats.stream()
                .map(seat -> {
                    // 예약이 있는 경우에만 ReservationDTO 생성
                    ReservationDTO reservationDTO = null;
                    if (seat.getReservation() != null) {
                        reservationDTO = new ReservationDTO(
                                seat.getReservation().getId(),
                                seat.getReservation().getName(),
                                seat.getReservation().getPhoneNum(),
                                seat.getReservation().getDateTime(),
                                seat.getReservation().getGuestCount(),
                                seat.getReservation().getApprovalState()
                        );
                    }

                    return new SeatDTO(
                            seat.getId(),
                            seat.getSeatType(),
                            seat.getPosX(),
                            seat.getPosY(),
                            seat.isAvailable(),
                            seat.getTableCapacity(),
                            reservationDTO
                    );
                })
                .collect(Collectors.toList());

        for (SeatDTO seatDTO : seatDTOs) {
            log.info("현황 좌석 정보 = {}", seatDTO.getSeatType());
            log.info("= {}", seatDTO.getTableCapacity());
            log.info("= {}", seatDTO.getPosX());
            log.info("= {}", seatDTO.getPosY());

            if (seatDTO.getReservationDTO() != null) {
                log.info("예약 정보 = {}", seatDTO.getReservationDTO().getName());
                log.info("= {}", seatDTO.getReservationDTO().getPhoneNum());
                log.info("= {}", seatDTO.getReservationDTO().getDateTime());
                log.info("= {}", seatDTO.getReservationDTO().getGuestCount());
                log.info("= {}", seatDTO.getReservationDTO().getApprovalState());
            }
        }

        return ResponseEntity.ok(seatDTOs);
    }


    @PostMapping("/api/update/available/{seatId}")
    public ResponseEntity<?> updateAvailable(@PathVariable Long seatId) {
        log.info("선택한 좌석 Id ={}", seatId);

        // 만약 예약 중인 경우 상태 변경
        reservationService.cancelReservation(seatId);

        SeatDTO seatDTO = seatService.changeAvailable(seatId);

        return ResponseEntity.ok(seatDTO);
    }
}
