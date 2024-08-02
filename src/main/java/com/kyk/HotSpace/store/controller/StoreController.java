package com.kyk.HotSpace.store.controller;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.MemberDto;
import com.kyk.HotSpace.store.domain.dto.StoreUploadForm;
import com.kyk.HotSpace.store.service.StoreServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreServiceImpl storeService;

    @GetMapping("/upload")
    public String uploadForm(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDto loginMember,
                             @ModelAttribute("uploadForm") StoreUploadForm storeUploadForm,
                             Model model) {
        // 세션에 회원 데이터가 없으면 홈 화면으로 이동
        if(loginMember == null) {
            log.info("로그인 상태가 아님");

            model.addAttribute("message", "회원만 글을 작성할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        return "stores/store_upload";
    }

    @PostMapping("/upload")
    public String storeUpload(@SessionAttribute(LoginSessionConst.LOGIN_MEMBER) MemberDto loginMember,
                              @Valid @ModelAttribute("uploadForm") StoreUploadForm form, BindingResult bindingResult) throws IOException {
        log.info("가게 이름 = {}", form.getName());
        log.info("가게 연락처 = {}", form.getNumber());
        log.info("가게 url = {}", form.getSiteUrl());
        log.info("가게 좌표 = {}, {}", form.getLatitude(), form.getLongitude());
        log.info("가게 주소 = {}", form.getAddress());

        if (bindingResult.hasErrors()) {
            return "stores/store_upload";
        }

        storeService.saveStore(loginMember.getId(), form);
        return "redirect:/";
    }
}
