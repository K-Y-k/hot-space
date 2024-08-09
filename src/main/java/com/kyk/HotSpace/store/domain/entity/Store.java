package com.kyk.HotSpace.store.domain.entity;

import com.kyk.HotSpace.file.domain.StoreFile;
import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.domain.entity.Role;
import com.kyk.HotSpace.web.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Long id;

    private String category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(length = 15, nullable = false)
    private String number;

    private String siteUrl;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @ManyToOne(fetch = FetchType.LAZY) // Store 입장에서는 member와 다대일 관계이므로
    @JoinColumn(name = "member_id")    // Store 엔티티는 Member 엔티티의 id 필드를 "member_id"라는 이름으로 외래 키를 가짐
    private Member member;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<StoreFile> storeFiles = new ArrayList<>();
}
