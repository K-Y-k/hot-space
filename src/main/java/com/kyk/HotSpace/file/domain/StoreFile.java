package com.kyk.HotSpace.file.domain;

import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.web.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class StoreFile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeFile_id")
    private Long id;

    private String originalFileName;

    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;


    public static StoreFile toStoreFileEntity(Store store, String originalFileName, String storedFileName) {
        return StoreFile.builder()
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .store(store)
                .build();
    }
}