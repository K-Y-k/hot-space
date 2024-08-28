package com.kyk.HotSpace.reservation.controller;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.reservation.domain.dto.ReservationUploadForm;
import com.kyk.HotSpace.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        reservationService.saveReservation(form, convertDateTime);

        model.addAttribute("message", "예약 되었습니다!");
        model.addAttribute("redirectUrl", "/");
        return "messages";
    }
}
