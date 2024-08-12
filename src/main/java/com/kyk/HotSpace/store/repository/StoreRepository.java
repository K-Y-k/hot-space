package com.kyk.HotSpace.store.repository;

import com.kyk.HotSpace.store.domain.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * 회원 리포지토리의 인터페이스
 */
public interface StoreRepository {
    Store save(Store store);
    Optional<Store> findById(Long id);
    void delete(Long storeId);
    void clear();

    List<Store> findStoreWithinRadius(double lat, double lng, double radius);
    List<Store> findStoreWithinRadiusByCategory(double lat, double lng, double radius, String category);

    Page<Store> findStoresByMemberId(Long memberId, Pageable pageable);
}
