package com.kyk.HotSpace.member.repository;

import com.kyk.HotSpace.member.domain.entity.Member;

import java.util.Optional;

/**
 * 회원 리포지토리의 인터페이스
 */
public interface MemberRepository {
    Member saveMember(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByName(String name);
    void delete(Long memberId);
    void clear();
}
