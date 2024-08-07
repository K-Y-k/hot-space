package com.kyk.HotSpace.store.service;

import com.kyk.HotSpace.store.domain.dto.StoreDTO;
import com.kyk.HotSpace.store.domain.dto.StoreUploadForm;
import com.kyk.HotSpace.store.domain.dto.StoreUpdateForm;
import com.kyk.HotSpace.store.domain.entity.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService {
    Optional<Store> findById(Long id);
    Long saveStore(Long memberId, StoreUploadForm form);

    void changeStore(Long memberId, StoreUpdateForm form);

    void deleteStore(Long memberId);

    List<Store> findMarkersWithinRadius(double lat, double lng, double radius);

    List<Store> findMarkersWithinRadiusByCategory(double center_lat, double center_lng, double radiusIn, String category);
}
