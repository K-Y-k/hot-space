package com.kyk.HotSpace.file.repository.member;

import com.kyk.HotSpace.file.domain.ProfileFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringDataJpaProfileFileRepository implements ProfileFileRepository {

    private final JPAProfileFileRepository JPAProfileFileRepository;

    @Override
    public ProfileFile saveProfile(ProfileFile profileFile) {
        return JPAProfileFileRepository.save(profileFile);
    }

    @Override
    public Optional<ProfileFile> findByMemberId(Long memberId) {
        return JPAProfileFileRepository.findByMemberId(memberId);
    }

    @Override
    public void deleteByMemberId(Long profileFileId) {
        JPAProfileFileRepository.deleteByMemberId(profileFileId);
    }
}
