package com.kyk.HotSpace.web;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.domain.entity.Role;
import com.kyk.HotSpace.member.repository.SpringDataJpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;


@Profile("local")
@Controller
@RequiredArgsConstructor
public class TestDataInit {
    private final SpringDataJpaMemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        // 회원 데이터 추가 총 3개
        memberRepository.saveMember(new Member(1L, "admin", "qq", "qq", Role.ADMIN));
        memberRepository.saveMember(new Member(2L, "ddd", "dd", "dd", Role.CUSTOMER));
        memberRepository.saveMember(new Member(3L, "aaa", "aa", "aa", Role.CEO));
    }

}
