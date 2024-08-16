package com.kyk.HotSpace.seat.repository;

import com.kyk.HotSpace.seat.domain.entity.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Optional<Seat> findById(Long id) {
        return JPASeatRepository.findById(id);
    }

    @Override
    public Optional<Seat> findFirstByStoreId(Long storeId) {
        return JPASeatRepository.findFirstByStoreId(storeId);
    }

    @Override
    public List<Seat> findByStoreId(Long storeId) {
        return JPASeatRepository.findByStoreId(storeId);
    }

    @Override
    public void deleteAllByStoreId(Long storeId) {
        JPASeatRepository.deleteAllByStoreId(storeId);
    }

    @Override
    public void clear() {
        JPASeatRepository.deleteAll();
    }
}
