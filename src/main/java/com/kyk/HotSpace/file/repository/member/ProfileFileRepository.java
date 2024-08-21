package com.kyk.HotSpace.file.repository.member;

import com.kyk.HotSpace.file.domain.ProfileFile;
import com.kyk.HotSpace.member.domain.entity.Member;

import java.util.Optional;

public interface ProfileFileRepository {
    ProfileFile saveProfile(ProfileFile profileFile);
    Optional<ProfileFile> findByMemberId(Long memberId);
    void deleteByMemberId(Long profileFileId);
}
