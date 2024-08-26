package com.kyk.HotSpace.file.repository.store;

import com.kyk.HotSpace.file.domain.StoreFile;

import java.util.List;

public interface StoreFileRepository {
    StoreFile saveProfile(StoreFile storeFile);
    List<StoreFile> findByStoreId(Long storeId);
    void deleteByStoreId(Long storeId);
}
