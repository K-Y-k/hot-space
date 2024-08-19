package com.kyk.HotSpace.store.domain.dto;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.store.domain.entity.Store;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreUploadForm {

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


    // 엔티티에 @setter를 사용하지 않기 위해 dto에서 엔티티로 변환해주는 메서드 적용
    public Store toEntity(Member member) {
        return Store.builder()
                .category(category)
                .name(name)
                .address(address)
                .number(number)
                .siteUrl(siteUrl)
                .latitude(latitude)
                .longitude(longitude)
                .member(member)
                .build();
    }
}
