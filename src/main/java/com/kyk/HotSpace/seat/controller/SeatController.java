package com.kyk.HotSpace.seat.controller;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.reservation.domain.dto.ReservationUploadForm;
import com.kyk.HotSpace.reservation.domain.entity.ApprovalState;
import com.kyk.HotSpace.reservation.domain.entity.Reservation;
import com.kyk.HotSpace.reservation.service.ReservationService;
import com.kyk.HotSpace.seat.domain.dto.SeatStatistics;
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

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;
    private final ReservationService reservationService;

    @GetMapping("/{storeId}/setting")
    public String seatsForm(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                            @PathVariable Long storeId,
                            Model model) {
        // 세션 회원 검증
        if (loginMember == null) {
            log.info("로그인 상태가 아님");

            model.addAttribute("message", "회원만 이용할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        model.addAttribute("storeId", storeId);

        return "seats/seat_setting";
    }

    @GetMapping("/{storeId}/state")
    public String seatsStateForm(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                                 @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                 @PathVariable Long storeId,
                                 Model model) {
        // 세션 회원 검증
        if (loginMember == null) {
            log.info("로그인 상태가 아님");

            model.addAttribute("message", "회원만 이용할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        // 테이블 현황 통계 내역 가져오기
        SeatStatistics seatStatistics = seatService.statisticsResult(storeId);

        model.addAttribute("totalCount", seatStatistics.getTotalCount());
        model.addAttribute("usingCount", seatStatistics.getUsingCount());
        model.addAttribute("reservationCount", seatStatistics.getReservationCount());
        model.addAttribute("remainingCount", seatStatistics.getRemainingCount());
        model.addAttribute("storeId", storeId);


        // 해당 가게에 승인 처리 되지 않은 예약 페이지 가져오기
        Page<Reservation> findReservations = reservationService.findByStoreIdAndApprovalState(pageable, storeId, ApprovalState.STAND);

        // 페이지 번호 설정
        int nowPage = pageable.getPageNumber() + 1;
        int startPage = Math.max(1, nowPage - 2);
        int endPage = Math.min(nowPage + 2, findReservations.getTotalPages());

        // 예약, 페이지 설정 모델 등록
        model.addAttribute("reservations", findReservations);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // 이전, 다음페이지
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());

        // 이전, 다음 페이지의 존재 여부
        model.addAttribute("hasPrev", findReservations.hasPrevious());
        model.addAttribute("hasNext", findReservations.hasNext());

        return "seats/seat_state";
    }

    @GetMapping("/{storeId}/state/view")
    public String seatsView(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                            @ModelAttribute("uploadForm") ReservationUploadForm form,
                            @PathVariable Long storeId,
                            Model model) {

        SeatStatistics seatStatistics = seatService.statisticsResult(storeId);

        if (loginMember != null) {
            form.setName(loginMember.getName());
        }
        form.setGuestCount(1);

        model.addAttribute("totalCount", seatStatistics.getTotalCount());
        model.addAttribute("usingCount", seatStatistics.getUsingCount());
        model.addAttribute("reservationCount", seatStatistics.getReservationCount());
        model.addAttribute("remainingCount", seatStatistics.getRemainingCount());
        model.addAttribute("storeId", storeId);

        return "seats/seat_state_view";
    }

}
