package com.kyk.HotSpace.file.repository.store;

import com.kyk.HotSpace.file.domain.StoreFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAStoreFileRepository extends JpaRepository<StoreFile, Long> {
}
