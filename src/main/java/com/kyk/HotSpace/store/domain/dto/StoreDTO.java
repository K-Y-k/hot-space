package com.kyk.HotSpace.store.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

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
}
