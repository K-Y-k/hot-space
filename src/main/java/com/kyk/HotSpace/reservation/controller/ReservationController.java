package com.kyk.HotSpace.reservation.controller;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.reservation.domain.dto.ReservationUploadForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    @PostMapping("/upload/{storeId}/{seatId}")
    public String reservationUploadForm(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                                        @ModelAttribute("uploadForm") ReservationUploadForm form,
                                        @PathVariable Long storeId, @PathVariable Long seatId,
                                        Model model) {
        // 세션 회원 검증
        if(loginMember == null) {
            model.addAttribute("message", "회원만 예약할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        model.addAttribute("message", "예약 되었습니다!");
        model.addAttribute("redirectUrl", "/");
        return "messages";
    }
}
