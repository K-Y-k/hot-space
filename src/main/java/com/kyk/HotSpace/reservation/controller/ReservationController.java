package com.kyk.HotSpace.reservation.controller;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.reservation.domain.dto.ReservationUploadForm;
import com.kyk.HotSpace.reservation.domain.entity.ApprovalState;
import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import com.kyk.HotSpace.reservation.service.ReservationService;
import com.kyk.HotSpace.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final SeatService seatService;
    private final ReservationService reservationService;

    @PostMapping("/upload/{storeId}")
    public String reservationUploadForm(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                                        @ModelAttribute("uploadForm") ReservationUploadForm form,
                                        @PathVariable Long storeId,
                                        Model model) {
        // 세션 회원 검증
        if(loginMember == null) {
            model.addAttribute("message", "회원만 예약할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        LocalDateTime convertDateTime = LocalDateTime.parse(form.getDateTime(), DateTimeFormatter.ISO_DATE_TIME);

        log.info("예약 좌석Id = {}", form.getSeatId());
        log.info("예약 성함 = {}", form.getName());
        log.info("예약 연락처 = {}", form.getPhoneNum());
        log.info("예약 날짜 = {}", convertDateTime);
        log.info("예약 참석인원 = {}", form.getGuestCount());

        reservationService.saveReservation(form, convertDateTime, loginMember.getId(), storeId);

        model.addAttribute("message", "예약이 신청되었습니다!\n예약 내역에서 승인 처리되어야 예약이 확정됩니다.\n확인한 후 방문해 주세요!");
        model.addAttribute("redirectUrl", "/reservations/list");
        return "messages";
    }


    @GetMapping("/list")
    public String reservationList(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                                  @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                  Model model) {
        // 세션 회원 검증
        if(loginMember == null) {
            model.addAttribute("message", "회원만 이용할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        // 예약 페이징
        Page<Reservation> reservations = reservationService.findReservationsByMemberId(loginMember.getId(), pageable);

        // 페이지 번호 설정
        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(1, nowPage - 2);
        int endPage = Math.min(nowPage + 2, reservations.getTotalPages());

        // 예약, 페이지 설정 모델 등록
        model.addAttribute("reservations", reservations);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // 이전, 다음페이지
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());

        // 이전, 다음 페이지의 존재 여부
        model.addAttribute("hasPrev", reservations.hasPrevious());
        model.addAttribute("hasNext", reservations.hasNext());


        return "reservations/reservation_list";
    }


    @GetMapping("/{storeId}/{reservationId}/approve")
    public String approveReservation(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                                     @RequestParam String state,
                                     @PathVariable Long storeId,
                                     @PathVariable Long reservationId,
                                     Model model) {
        // 세션 회원 검증
        if(loginMember == null) {
            model.addAttribute("message", "회원만 이용할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        ApprovalState approvalState = null;

        if (state.equals("YES")) {
            approvalState = ApprovalState.APPROVE;
            seatService.changeAvailable(reservationService.findSeatId(reservationId));
        }
        if (state.equals("NO")) {
            approvalState = ApprovalState.REJECT;
        }

        reservationService.changeApprovalState(reservationId, approvalState);

        model.addAttribute("message", "처리 완료");
        model.addAttribute("redirectUrl", "/seats/" + storeId + "/state");
        return "messages";
    }

    @GetMapping("/{reservationId}/delete")
    public String deleteReservation(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                                    @PathVariable Long reservationId,
                                    Model model) {
        // 세션 회원 검증
        if(loginMember == null) {
            model.addAttribute("message", "회원만 이용할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        reservationService.deleteReservation(reservationId);

        model.addAttribute("message", "처리 되었습니다");
        model.addAttribute("redirectUrl", "/reservations/list");
        return "messages";
    }
}
