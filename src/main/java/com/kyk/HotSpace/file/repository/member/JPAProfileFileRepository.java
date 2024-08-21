package com.kyk.HotSpace.file.repository.member;

import com.kyk.HotSpace.file.domain.ProfileFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JPAProfileFileRepository extends JpaRepository<ProfileFile, Long> {
    Optional<ProfileFile> findByMemberId(Long memberId);
    void deleteByMemberId(Long memberId);
}
