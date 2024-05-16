package com.kyk.HotSpace.member.domain.dto;

import com.kyk.HotSpace.member.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원의 민감한 정보를 제외한 내용만 담을 DTO
 */
@Getter
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String name;
    private Role role;
}
