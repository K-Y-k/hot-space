package com.kyk.HotSpace.file.repository.member;

import com.kyk.HotSpace.file.domain.ProfileFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAProfileFileRepository extends JpaRepository<ProfileFile, Long> {
}
