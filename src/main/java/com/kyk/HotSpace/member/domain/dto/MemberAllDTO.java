package com.kyk.HotSpace.member.domain.dto;

import com.kyk.HotSpace.member.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberAllDTO {
    private Long id;
    private String name;
    private String loginId;
    private String password;
    private Role role;
    private String storedFileName;
}
