package com.kyk.HotSpace.member.domain.entity;

import com.kyk.HotSpace.file.domain.ProfileFile;
import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import com.kyk.HotSpace.store.domain.entity.Store;
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
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(length = 7, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String loginId;

    @Column(length = 10, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private ProfileFile profileFile;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reservation> reservations;

    public Member(String name, String loginId, String password, Role role) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }


    // 비즈니스 로직
    // 변경 감지로 프로필 업데이트
    public void changeName(String name){
        this.name = name;
    }
    public void changePassword(String password){
        this.password = password;
    }


    public void changeRole(Role role) {
        this.role = role;
    }
}
