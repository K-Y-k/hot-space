package com.kyk.HotSpace.member.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    CUSTOMER("ROLE_CUSTOMER", "고객"),
    CEO("ROLE_CEO", "사장"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}
