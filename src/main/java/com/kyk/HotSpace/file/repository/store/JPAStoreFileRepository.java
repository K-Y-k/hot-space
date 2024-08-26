package com.kyk.HotSpace.file.repository.store;

import com.kyk.HotSpace.file.domain.StoreFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JPAStoreFileRepository extends JpaRepository<StoreFile, Long> {
    List<StoreFile> findByStoreId(Long storeId);
    void deleteByStoreId(Long memberId);
}
