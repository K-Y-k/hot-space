package com.kyk.HotSpace.file.domain;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.web.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class ProfileFile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profileFile_id")
    private Long id;

    private String originalFileName;

    private String storedFileName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public static ProfileFile toProfileFileEntity(Member member, String originalFileName, String storedFileName) {
        return ProfileFile.builder()
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .member(member)
                .build();
    }
}