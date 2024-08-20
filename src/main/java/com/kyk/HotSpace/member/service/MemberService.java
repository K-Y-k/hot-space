package com.kyk.HotSpace.member.service;

import com.kyk.HotSpace.member.domain.dto.JoinForm;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.member.domain.dto.UpdateForm;

import java.io.IOException;

public interface MemberService {
    Long join(JoinForm form) throws IOException;

    MemberDTO login(String loginId, String password);

    MemberDTO findMemberDtoById(Long memberId);

    void changeProfile(Long memberId, UpdateForm form);

    void delete(Long memberId);
}
