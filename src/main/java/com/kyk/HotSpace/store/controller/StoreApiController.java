package com.kyk.HotSpace.store.controller;

import com.kyk.HotSpace.store.domain.dto.StoreDTO;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.service.StoreServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreApiController {
    private final StoreServiceImpl storeService;

    /**
     * 현재 중심 반경 500m 가게 찾기
     */
    @GetMapping("/api/checkStore")
    public ResponseEntity<?> checkStore(double lat, double lng, double radius) {

        log.info("현재 중심 위도 = {}, 경도 = {}", lat, lng);
        log.info("반경 = {}", radius);

        double radiusIn = radius / 1000;
        List<Store> findStores = storeService.findMarkersWithinRadius(lat, lng, radiusIn);

        List<StoreDTO> findStoresDTOList = findStores.stream()
                .map(m -> new StoreDTO(m.getId(), m.getName(), m.getAddress(), m.getNumber(), m.getNumber(), m.getLatitude(), m.getLongitude()))
                .collect(Collectors.toList());

        for (StoreDTO storeDTO : findStoresDTOList) {
            log.info("찾은 가게 = {}", storeDTO.getName());
        }

        return new ResponseEntity<>(findStoresDTOList, HttpStatus.OK);
    }
}
