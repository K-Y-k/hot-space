package com.kyk.HotSpace.file.repository.store;

import com.kyk.HotSpace.file.domain.StoreFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SpringDataJpaStoreFileRepository implements StoreFileRepository {

    private final JPAStoreFileRepository JPAStoreFileRepository;

    @Override
    public StoreFile saveProfile(StoreFile storeFile) {
        return JPAStoreFileRepository.save(storeFile);
    }
}
