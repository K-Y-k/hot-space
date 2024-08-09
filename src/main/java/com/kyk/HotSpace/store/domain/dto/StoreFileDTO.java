package com.kyk.HotSpace.store.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreFileDTO {
    private String originalFileName;

    private String storedFileName;
}
