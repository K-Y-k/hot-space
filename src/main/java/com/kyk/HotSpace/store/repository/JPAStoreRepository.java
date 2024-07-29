package com.kyk.HotSpace.store.repository;

import com.kyk.HotSpace.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JPAStoreRepository extends JpaRepository<Store, Long> {
}
