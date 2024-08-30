package com.kyk.HotSpace.seat.controller;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.reservation.domain.dto.ReservationUploadForm;
import com.kyk.HotSpace.seat.domain.dto.SeatStatistics;
import com.kyk.HotSpace.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;

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
