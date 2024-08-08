package com.kyk.HotSpace.file.repository.member;

import com.kyk.HotSpace.file.domain.ProfileFile;
import com.kyk.HotSpace.member.domain.entity.Member;

public interface ProfileFileRepository {
    ProfileFile saveProfile(ProfileFile profileFile);
}
