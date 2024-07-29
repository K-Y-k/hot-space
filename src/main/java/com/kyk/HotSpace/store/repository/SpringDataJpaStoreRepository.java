package com.kyk.HotSpace.store.repository;

import com.kyk.HotSpace.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
