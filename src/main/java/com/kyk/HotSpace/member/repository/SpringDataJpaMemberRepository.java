package com.kyk.HotSpace.member.repository;

import com.kyk.HotSpace.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 스프링 데이터 JPA로 구현한 회원 리포지토리
 */
@Repository
@RequiredArgsConstructor
public class SpringDataJpaMemberRepository implements MemberRepository{

    private final JPAMemberRepository JPAmemberRepository;

    @Override
    public Member saveMember(Member member) {
        return JPAmemberRepository.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return JPAmemberRepository.findById(id);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return JPAmemberRepository.findByLoginId(loginId);
    }

    @Override
    public Optional<Member> findByName(String name) {
        return JPAmemberRepository.findByName(name);
    }

    @Override
    public void delete(Long memberId) {
        JPAmemberRepository.deleteById(memberId);
    }

    @Override
    public void clear() {
        JPAmemberRepository.deleteAll();
    }
}
