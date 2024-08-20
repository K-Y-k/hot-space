package com.kyk.HotSpace.member.service;

import com.kyk.HotSpace.exception.member.DuplicatedException;
import com.kyk.HotSpace.exception.member.MemberNotFoundException;
import com.kyk.HotSpace.file.domain.ProfileFile;
import com.kyk.HotSpace.file.repository.member.ProfileFileRepository;
import com.kyk.HotSpace.member.domain.dto.JoinForm;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.member.domain.dto.UpdateForm;
import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ProfileFileRepository profileFileRepository;

    @Value("${file.profileLocation}")
    private String profileLocation;

    @Override
    public Long join(JoinForm form) throws IOException {
        Member memberEntity = form.toEntity();

        // 닉네임/아이디 중복 회원 검증 로직
        validateDuplicateMember(memberEntity);

        // 회원 엔티티 저장
        Member saveMember = memberRepository.saveMember(memberEntity);

        // 프로필 사진 저장
        MultipartFile profileImage = form.getProfileImage();
        uploadProfileFile(profileImage, memberEntity);

        return saveMember.getId();
    }

    private void uploadProfileFile(MultipartFile profileImage, Member memberEntity) throws IOException {
        // 오리지널, 실제 파일 이름 초기화
        String originalFileName = "default_icon.PNG";
        String storedFileName = "default_icon.PNG";

        // 빈 파일 검증 로직
        if (!profileImage.getOriginalFilename().isBlank()) {
            // 오리지널 파일 이름 지정
            originalFileName = profileImage.getOriginalFilename();
            
            // 파일에 이름을 붙일 랜덤으로 식별자 지정
            UUID uuid = UUID.randomUUID();
            storedFileName = uuid + "_" + originalFileName;
            String savePath = profileLocation;

            // 실제 파일 저장 경로와 파일 이름 지정한 File 객체 생성 및 저장
            profileImage.transferTo(new File(savePath, storedFileName));
        }

        // 프로필 파일 엔티티 저장
        ProfileFile profileFile = ProfileFile.toProfileFileEntity(memberEntity, originalFileName, storedFileName);
        profileFileRepository.saveProfile(profileFile);
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
    public MemberDTO login(String loginId, String password) {
        // 람다를 활용하여 축약
        Member loginMember = memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElseThrow(() -> new MemberNotFoundException("아이디 또는 패스워드가 일치하지 않습니다."));

        String storedFileName = (loginMember.getProfileFile() != null)
                ? loginMember.getProfileFile().getStoredFileName()
                : "defaultFile"; // 기본값 설정

        return new MemberDTO(loginMember.getId(), loginMember.getName(), loginMember.getRole(), storedFileName);
    }


    @Override
    public MemberDTO findMemberDtoById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 찾기 실패: 회원을 찾을 수 없습니다." + memberId));

        String storedFileName = "defaultFileName";
        if (findMember.getProfileFile() != null) {
            storedFileName = findMember.getProfileFile().getStoredFileName();
        }

        return new MemberDTO(findMember.getId(), findMember.getName(),  findMember.getRole(), storedFileName);
    }


    @Override
    public void changeProfile(Long memberId, UpdateForm form) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 찾기 실패: 회원을 찾을 수 없습니다." + memberId));

        if (form.getName() != null) {
            log.info("받아온 이름={}", form.getName());
            findMember.changeName(form.getName());
        }

        if (form.getPassword() != null) {
            log.info("받아온 비밀번호={}", form.getPassword());
            findMember.changePassword(form.getPassword());
        }


        log.info("업데이트 완료");
    }


    @Override
    public void delete(Long memberId) {
        memberRepository.delete(memberId);
    }
}
