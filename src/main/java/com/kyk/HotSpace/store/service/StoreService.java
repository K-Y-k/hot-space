package com.kyk.HotSpace.store.service;

import com.kyk.HotSpace.store.domain.dto.StoreDTO;
import com.kyk.HotSpace.store.domain.dto.StoreUploadForm;
import com.kyk.HotSpace.store.domain.dto.StoreUpdateForm;
import com.kyk.HotSpace.store.domain.entity.Store;

import java.util.List;

public interface StoreService {
    Long saveStore(Long memberId, StoreUploadForm form);

    void changeStore(Long memberId, StoreUpdateForm form);

    void deleteStore(Long memberId);

    List<Store> findMarkersWithinRadius(double lat, double lng, double radius);
}
