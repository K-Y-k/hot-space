package com.kyk.HotSpace.member.domain.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 로그인시 사용될 DTO
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginForm {
    @NotBlank(message = "아이디를 입력해주세요")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
