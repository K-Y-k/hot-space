package com.kyk.HotSpace.web;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.MemberDto;
import com.kyk.HotSpace.member.domain.entity.Member;
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
    public String home(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDto loginMember,
                       Model model) {
        if (loginMember == null) {
            return "redirect:/members/login";
        }

        return "main";
    }
}
