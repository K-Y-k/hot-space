package com.kyk.HotSpace.store.domain.dto;

import com.kyk.HotSpace.file.domain.StoreFile;
import com.kyk.HotSpace.store.domain.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class StoreDTO {
    private Long id;
    private String category;
    private String name;
    private String address;
    private String number;
    private String siteUrl;
    private double latitude;
    private double longitude;
    private List<String> imageFileName;


    public StoreDTO(long id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StoreDTO(Store store) {
        this.id = store.getId();
        this.category = store.getCategory();
        this.name = store.getName();
        this.address = store.getAddress();
        this.number = store.getNumber();
        this.siteUrl = store.getSiteUrl();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.imageFileName = store.getStoreFiles().stream()
                .map(StoreFile::getStoredFileName)
                .collect(Collectors.toList());
    }
}
