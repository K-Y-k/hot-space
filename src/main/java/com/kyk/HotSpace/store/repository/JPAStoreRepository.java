package com.kyk.HotSpace.store.repository;

import com.kyk.HotSpace.store.domain.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface JPAStoreRepository extends JpaRepository<Store, Long> {
    @Query("select s from Store s " +
            "where (6371 * acos(cos(radians(:lat)) * cos(radians(s.latitude)) * cos(radians(s.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(s.latitude)))) < :radius")
    List<Store> findStoreWithinRadius(double lat, double lng, double radius);

    @Query("select s from Store s " +
            "where (6371 * acos(cos(radians(:lat)) * cos(radians(s.latitude)) * cos(radians(s.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(s.latitude)))) < :radius " +
            "and s.category = :category")
    List<Store> findStoreWithinRadiusByCategory(double lat, double lng, double radius, String category);

    Page<Store> findStoresByMemberId(Long memberId, Pageable pageable);
}
