package com.kyk.HotSpace.store.service;

import com.kyk.HotSpace.store.domain.dto.StoreDTO;
import com.kyk.HotSpace.store.domain.dto.StoreUploadForm;
import com.kyk.HotSpace.store.domain.dto.StoreUpdateForm;
import com.kyk.HotSpace.store.domain.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface StoreService {
    StoreDTO findById(Long storeId);
    Long saveStore(Long memberId, StoreUploadForm form) throws IOException;

    void changeStore(Long storeId, StoreUpdateForm form) throws IOException;

    void deleteStore(Long memberId);

    List<Store> findMarkersWithinRadius(double lat, double lng, double radius);

    List<Store> findMarkersWithinRadiusByCategory(double center_lat, double center_lng, double radiusIn, String category);
    Page<Store> findStoresByMemberId(Long memberId, Pageable pageable);
}
