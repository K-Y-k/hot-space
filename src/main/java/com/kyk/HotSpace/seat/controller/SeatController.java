package com.kyk.HotSpace.seat.controller;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.MemberDto;
import com.kyk.HotSpace.seat.domain.dto.SeatStatistics;
import com.kyk.HotSpace.seat.domain.entity.Seat;
import com.kyk.HotSpace.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/{storeId}/setting")
    public String seatsForm(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDto loginMember,
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
    public String seatsStateForm(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDto loginMember,
                                 @PathVariable Long storeId,
                                 Model model) {
        // 세션 회원 검증
        if (loginMember == null) {
            log.info("로그인 상태가 아님");

            model.addAttribute("message", "회원만 이용할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        SeatStatistics seatStatistics = seatService.statisticsResult(storeId);

        model.addAttribute("totalCount", seatStatistics.getTotalCount());
        model.addAttribute("usingCount", seatStatistics.getUsingCount());
        model.addAttribute("remainingCount", seatStatistics.getRemainingCount());
        model.addAttribute("storeId", storeId);

        return "seats/seat_state";
    }
}
