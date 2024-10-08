package com.kyk.HotSpace.web;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                       Model model) {
        log.info("회원 세션 유무 = {}", loginMember);

        return "main";
    }
}
