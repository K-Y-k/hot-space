package com.kyk.HotSpace.seat.domain.entity;

import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import com.kyk.HotSpace.seat.domain.dto.SeatDTO;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.web.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String seatType;

    @Column(nullable = false)
    private int tableCapacity;

    @Column(nullable = false)
    private double posX;

    @Column(nullable = false)
    private double posY;

    @Column(nullable = false)
    private boolean available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(mappedBy = "seat", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Reservation reservation;

    // 변경감지 메서드
    public void changeAvailable() {
        available = !available;
    }
}
