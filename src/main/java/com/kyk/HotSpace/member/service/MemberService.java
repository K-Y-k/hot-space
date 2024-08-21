package com.kyk.HotSpace.member.service;

import com.kyk.HotSpace.member.domain.dto.JoinForm;
import com.kyk.HotSpace.member.domain.dto.MemberAllDTO;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.member.domain.dto.UpdateForm;

import java.io.IOException;

public interface MemberService {
    Long join(JoinForm form) throws IOException;

    MemberDTO login(String loginId, String password);

    MemberAllDTO findMemberDtoById(Long memberId);

    void changeProfile(MemberDTO loginMember, UpdateForm form) throws IOException;

    void delete(Long memberId);
}
