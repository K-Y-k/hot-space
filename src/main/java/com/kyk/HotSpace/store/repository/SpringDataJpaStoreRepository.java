package com.kyk.HotSpace.store.repository;

import com.kyk.HotSpace.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringDataJpaStoreRepository implements StoreRepository{

    private final JPAStoreRepository JPAStoreRepository;

    @Override
    public Store save(Store store) {
        return JPAStoreRepository.save(store);
    }

    @Override
    public Optional<Store> findById(Long id) {
        return JPAStoreRepository.findById(id);
    }

    @Override
    public void delete(Long storeId) {
        JPAStoreRepository.deleteById(storeId);
    }

    @Override
    public void clear() {
        JPAStoreRepository.deleteAll();
    }

    @Override
    public List<Store> findStoreWithinRadius(double lat, double lng, double radius) {
        return JPAStoreRepository.findStoreWithinRadius(lat, lng, radius);
    }

    @Override
    public List<Store> findStoreWithinRadiusByCategory(double lat, double lng, double radius, String category) {
        return JPAStoreRepository.findStoreWithinRadiusByCategory(lat, lng, radius, category);
    }

    @Override
    public Page<Store> findStoresByMemberId(Long memberId, Pageable pageable) {
        return JPAStoreRepository.findStoresByMemberId(memberId, pageable);
    }
}
