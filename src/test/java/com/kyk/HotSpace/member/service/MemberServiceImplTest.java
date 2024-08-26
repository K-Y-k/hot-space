package com.kyk.HotSpace.member.service;

import com.kyk.HotSpace.member.domain.dto.MemberAllDTO;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.member.domain.dto.UpdateForm;
import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.domain.entity.Role;
import com.kyk.HotSpace.member.repository.SpringDataJpaMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest // 이 어노테이션으로 데이터베이스의 실행 없이도 자동으로 실행해준다.
public class MemberServiceImplTest {
    @Autowired MemberServiceImpl memberService;
    @Autowired SpringDataJpaMemberRepository memberRepository;

    @AfterEach
    void afterEach() {
        memberRepository.clear();
    }

    @Test
    @DisplayName("회원 엔티티 저장 및 조회")
    void save_findById() {
        // given
        Member member = new Member("memberA", "ID123", "pass123", Role.CUSTOMER);

        // when
        Member savedMember = memberRepository.saveMember(member);
        MemberAllDTO findMember = memberService.findMemberDtoById(member.getId());

        // then
        assertThat(findMember.getId()).isEqualTo(savedMember.getId());
    }

    @Test
    @DisplayName("회원가입")
    void join() throws IOException {
        // given
        Member member = new Member("memberA", "ID123", "pass123", Role.CUSTOMER);

        // when
        Member savedMember = memberRepository.saveMember(member);

        // then
        assertThat(member.getName()).isEqualTo(savedMember.getName());
    }

    @Test
    @DisplayName("로그인")
    void login() {
        // given
        Member member = new Member("memberA", "ID123", "pass123", Role.CUSTOMER);
        Member savedMember = memberRepository.saveMember(member);

        // when
        MemberDTO loginMember = memberService.login(savedMember.getLoginId(), savedMember.getPassword());

        // then
        assertThat(loginMember.getId()).isEqualTo(savedMember.getId());
    }


    @Test
    @DisplayName("회원 정보 수정")
    void update() throws IOException {
        // given
        Member member = new Member("memberA", "ID123", "pass123", Role.CUSTOMER);
        Member savedMember = memberRepository.saveMember(member);

        UpdateForm updateForm = new UpdateForm("수정된 이름", "수정된 비밀번호");

        // when
        savedMember.changeName(updateForm.getName());
        savedMember.changePassword(updateForm.getPassword());

        // then
        assertThat(savedMember.getName()).isEqualTo(updateForm.getName());
        assertThat(savedMember.getPassword()).isEqualTo(updateForm.getPassword());
    }


    @Test
    @DisplayName("회원 탈퇴")
    void delete() {
        // given
        Member member = new Member("memberA", "ID123", "pass123", Role.CUSTOMER);
        Member savedMember = memberRepository.saveMember(member);

        // when
        memberService.delete(savedMember.getId());

        // then
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                ()->{
                    memberService.findMemberDtoById(savedMember.getId());
                }
        );
        assertEquals("회원 찾기 실패: 회원을 찾을 수 없습니다." + savedMember.getId(), exception.getMessage());
    }

}
