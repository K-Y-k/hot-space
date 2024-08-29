package com.kyk.HotSpace.reservation.domain.entity;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.seat.domain.entity.Seat;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.web.BaseTimeEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 15, nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private int guestCount;

    @Column(nullable = false)
    private Boolean approvalState;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Seat seat;
}