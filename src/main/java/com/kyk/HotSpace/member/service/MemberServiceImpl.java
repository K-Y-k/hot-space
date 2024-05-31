package com.kyk.HotSpace.member.service;

import com.kyk.HotSpace.exception.member.DuplicatedException;
import com.kyk.HotSpace.exception.member.MemberNotFoundException;
import com.kyk.HotSpace.member.domain.dto.JoinForm;
import com.kyk.HotSpace.member.domain.dto.MemberDto;
import com.kyk.HotSpace.member.domain.dto.UpdateForm;
import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Override
    public Long join(JoinForm form) {
        Member memberEntity = form.toEntity();

        // 닉네임/아이디 중복 회원 검증 로직
        validateDuplicateMember(memberEntity);

        Member saveMember = memberRepository.saveMember(memberEntity);
        return saveMember.getId();
    }

    // 중복 검증 로직
    private void validateDuplicateMember(Member memberEntity) {
        // 중복이 있으면 EXCEPTION
        // 같은 로그인 아이디가 있는지 찾음
        // 반환형태가 Optional<Member>이므로 이렇게 예와처리 가능
        log.info("memberEntityId={}", memberEntity.getId());

        memberRepository.findByName(memberEntity.getName())
                .ifPresent(m -> {
                    throw new DuplicatedException("이미 존재하는 닉네임입니다.");
                });

        memberRepository.findByLoginId(memberEntity.getLoginId())
                .ifPresent(m -> {
                    throw new DuplicatedException("이미 존재하는 아이디입니다.");
                });
    }


    @Override
    public MemberDto login(String loginId, String password) {
        // 람다를 활용하여 축약
        Member loginMember = memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElseThrow(() -> new MemberNotFoundException("아이디 또는 패스워드가 일치하지 않습니다."));

        return new MemberDto(loginMember.getId(), loginMember.getName(), loginMember.getRole());
    }


    @Override
    public MemberDto findMemberDtoById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 찾기 실패: 회원을 찾을 수 없습니다." + memberId));

        return new MemberDto(findMember.getId(), findMember.getName(),  findMember.getRole());
    }


    @Override
    public void changeProfile(Long memberId, UpdateForm form) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 찾기 실패: 회원을 찾을 수 없습니다." + memberId));

        findMember.changeProfile(form.getName(), form.getLoginId(), form.getPassword());
    }


    @Override
    public void delete(Long memberId) {
        memberRepository.delete(memberId);
    }
}
