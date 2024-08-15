package com.kyk.HotSpace.seat.controller;

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

    @PostMapping("/api/upload/{storeId}")
    public ResponseEntity<?> seatUpload(@RequestBody List<SeatDTO> seatDTOs, @PathVariable Long storeId) {
        // 이전 좌석 데이터들 모두 삭제
        seatService.deleteAllByStoreId(storeId);

        log.info("가게 Id = {}", storeId);
        
        for (SeatDTO seatDTO : seatDTOs) {
            log.info("좌석 정보 = {}", seatDTO.getSeatType());
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
                .map(seat -> new SeatDTO(seat.getSeatType(), seat.getPosX(), seat.getPosY(), seat.getTableCapacity()))
                .collect(Collectors.toList());

        for (SeatDTO seatDTO : seatDTOs) {
            log.info("좌석 정보 = {}", seatDTO.getSeatType());
            log.info("= {}", seatDTO.getTableCapacity());
            log.info("= {}", seatDTO.getPosX());
            log.info("= {}", seatDTO.getPosY());

            seatService.saveSeat(seatDTO, storeId);
        }

        return ResponseEntity.ok(seatDTOs);
    }
}
