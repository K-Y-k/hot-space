package com.kyk.HotSpace.reservation.service;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.repository.MemberRepository;
import com.kyk.HotSpace.reservation.domain.dto.ReservationUploadForm;
import com.kyk.HotSpace.reservation.domain.entity.ApprovalState;
import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import com.kyk.HotSpace.reservation.repository.ReservationRepository;
import com.kyk.HotSpace.seat.domain.entity.Seat;
import com.kyk.HotSpace.seat.repository.SeatRepository;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final SeatRepository seatRepository;
    private final ReservationRepository reservationRepository;


    // DTO -> 엔티티 변환 메서드
    public Reservation toReservationEntity(ReservationUploadForm form, LocalDateTime convertDateTime, Member member, Store store, Seat seat) {
        return Reservation.builder()
                .name(form.getName())
                .phoneNum(form.getPhoneNum())
                .dateTime(convertDateTime)
                .guestCount(form.getGuestCount())
                .approvalState(ApprovalState.STAND)
                .member(member)
                .store(store)
                .seat(seat)
                .build();
    }

    @Override
    public void saveReservation(ReservationUploadForm form, LocalDateTime convertDateTime, Long memberId, Long storeId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("회원 가져오기 실패: 회왼을 찾지 못했습니다." + memberId));

        Store findStore = storeRepository.findById(storeId).orElseThrow(() ->
                new IllegalArgumentException("가게 가져오기 실패: 가게를 찾지 못했습니다." + storeId));

        Seat findSeat = seatRepository.findById(form.getSeatId()).orElseThrow(() ->
                new IllegalArgumentException("테이블 가져오기 실패: 테이블을 찾지 못했습니다." + form.getSeatId()));

        Reservation reservationEntity = toReservationEntity(form, convertDateTime, findMember, findStore, findSeat);

        reservationRepository.saveReservation(reservationEntity);
    }

    @Override
    public void changeApprovalState(Long reservationId, ApprovalState approvalState) {
        Reservation findReservation = reservationRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("예약 가져오기 실패: 예약을 찾지 못했습니다." + reservationId));

        findReservation.changeApprovalState(approvalState);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        Reservation findReservation = reservationRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("예약 가져오기 실패: 예약을 찾지 못했습니다." + reservationId));

        // 승인되어 이용좌석이 사용중으로 변경된 상황에서 고객이 삭제한 경우
        if (findReservation.getApprovalState() == ApprovalState.APPROVE && !findReservation.getSeat().isAvailable()) {
            findReservation.getSeat().changeAvailable();
        }

        reservationRepository.delete(reservationId);
    }

    @Override
    public Long findSeatId(Long reservationId) {
        Reservation findReservation = reservationRepository.findById(reservationId).orElseThrow(() ->
                new IllegalArgumentException("예약 가져오기 실패: 예약을 찾지 못했습니다." + reservationId));

        return findReservation.getSeat().getId();
    }

    @Override
    public void cancelReservation(Long seatId) {
        Optional<Reservation> findReservation = reservationRepository.findBySeatIdAndApprovalState(seatId, ApprovalState.STAND);
        findReservation.ifPresent(reservation -> reservation.changeApprovalState(ApprovalState.REJECT));
    }

    @Override
    public Page<Reservation> findReservationsByMemberId(Long memberId, Pageable pageable) {
        return reservationRepository.findReservationsByMemberId(memberId, pageable);
    }

    @Override
    public Page<Reservation> findByStoreIdAndApprovalState(Pageable pageable, Long storeId, ApprovalState approvalState) {
        return reservationRepository.findByStoreIdAndApprovalState(pageable, storeId, approvalState);
    }
}
