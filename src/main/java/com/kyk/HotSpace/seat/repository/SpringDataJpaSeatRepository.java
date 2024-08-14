package com.kyk.HotSpace.seat.repository;

import com.kyk.HotSpace.seat.domain.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class SpringDataJpaSeatRepository implements SeatRepository{
    private final JPASeatRepository JPASeatRepository;

    @Override
    public Seat saveSeat(Seat seat) {
        return JPASeatRepository.save(seat);
    }

    @Override
    public void clear() {
        JPASeatRepository.deleteAll();
    }
}
