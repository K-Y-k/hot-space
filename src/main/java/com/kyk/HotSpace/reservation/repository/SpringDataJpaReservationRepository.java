package com.kyk.HotSpace.reservation.repository;

import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringDataJpaReservationRepository implements ReservationRepository {

    private final JPAReservationRepository JPAReservationRepository;

    @Override
    public Reservation saveReservation(Reservation reservation) {
        return JPAReservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return JPAReservationRepository.findById(reservationId);
    }

    @Override
    public void delete(Long reservationId) {
        JPAReservationRepository.deleteById(reservationId);
    }

    @Override
    public void clear() {
        JPAReservationRepository.deleteAll();
    }
}