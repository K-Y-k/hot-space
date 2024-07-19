package com.kyk.HotSpace.store.repository;

import com.kyk.HotSpace.store.domain.entity.Store;

import java.util.Optional;

/**
 * 회원 리포지토리의 인터페이스
 */
public interface StoreRepository {
    Store save(Store store);
    Optional<Store> findById(Long id);
    void delete(Long storeId);
    void clear();
}
