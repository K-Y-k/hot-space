package com.kyk.HotSpace.member.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


/**
 * 스프링 데이터 JPA로 구현한 회원 리포지토리
 */
@Repository
@RequiredArgsConstructor
public class SpringDataJpaMemberRepository implements MemberRepository{

    private final JPAMemberRepository JPAmemberRepository;


}
