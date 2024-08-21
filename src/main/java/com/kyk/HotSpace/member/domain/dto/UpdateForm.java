package com.kyk.HotSpace.member.domain.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 * 회원 수정시 사용될 DTO
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateForm {
    @Size(min = 2, max = 7, message = "최소 2글자, 최대 7글자입니다.")
    private String name;

    @Size(min = 3, max = 10, message = "최소 3글자, 최대 10글자입니다.")
    private String password;

    private MultipartFile profileImage;


    public UpdateForm(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
