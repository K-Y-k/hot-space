package com.kyk.HotSpace.store.domain.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreUpdateForm {
    @NotEmpty(message = "시설 종류를 선택해주세요")
    private String category;

    @NotBlank(message = "가게 이름을 입력해주세요")
    private String name;

    @NotBlank(message = "주소를 입력해주세요")
    private String address;

    @NotBlank(message = "연락처를 입력해주세요(- 포함)")
    @Size(max = 15, message = "최대 12글자입니다.")
    private String number;

    private String siteUrl;

    @NotZero(message = "지도에 가게의 위치를 찍어주세요")
    private double latitude;

    private double longitude;

    private List<MultipartFile> imageFiles;

    public StoreUpdateForm(String category, String name, String address, String number) {
        this.category = category;
        this.name = name;
        this.address = address;
        this.number = number;
    }
}
