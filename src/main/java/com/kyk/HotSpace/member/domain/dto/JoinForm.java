package com.kyk.HotSpace.member.domain.dto;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.domain.entity.Role;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 회원 가입시 사용될 DTO
 */
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinForm {
    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(min = 2, max = 7, message = "최소 2글자, 최대 7글자입니다.")
    private String name;

    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 3, max = 10, message = "최소 3글자, 최대 10글자입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 3, max = 10, message = "최소 3글자, 최대 10글자입니다.")
    private String password;

    private MultipartFile profileImage;

    public JoinForm(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }

    // 엔티티에 @setter를 사용하지 않기 위해 dto에서 엔티티로 변환해주는 메서드 적용
    public Member toEntity() {
        return Member.builder()
                .name(name)
                .loginId(loginId)
                .password(password)
                .role(Role.CUSTOMER)
                .build();
    }
}
