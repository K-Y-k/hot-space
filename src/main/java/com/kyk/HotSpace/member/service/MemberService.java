package com.kyk.HotSpace.member.service;

import com.kyk.HotSpace.member.domain.dto.JoinForm;
import com.kyk.HotSpace.member.domain.dto.MemberDto;
import com.kyk.HotSpace.member.domain.dto.UpdateForm;

public interface MemberService {
    Long join(JoinForm form);

    MemberDto login(String loginId, String password);

    MemberDto findMemberDtoById(Long memberId);

    void changeProfile(Long memberId, UpdateForm form);

    void delete(Long memberId);
}
