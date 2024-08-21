package com.kyk.HotSpace.member.controller;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.JoinForm;
import com.kyk.HotSpace.member.domain.dto.LoginForm;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.member.domain.dto.UpdateForm;
import com.kyk.HotSpace.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServiceImpl memberService;


    /**
     * 회원 등록 폼
     */
    @GetMapping("/join")
    public String memberRegisterForm(@ModelAttribute("joinForm") JoinForm form) { // 컨트롤러에서 뷰로 넘어갈 때 이 데이터를 넣어 보낸다.
        return "members/member_join";
    }

    /**
     * 회원 저장 기능
     */
    @PostMapping("/join")
    public String save(@Valid @ModelAttribute("joinForm") JoinForm form,
                       BindingResult bindingResult,
                       Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            return "members/member_join";
        }

        log.info("회원가입 중 프로필 사진 오리지널 이름 = {}", form.getProfileImage().getOriginalFilename());

        memberService.join(form);
        model.addAttribute("message", "회원가입 되었습니다!");
        model.addAttribute("redirectUrl", "/");
        return "messages";
    }


    /**
     * 로그인 폼
     */
    @GetMapping("/login")
    public String memberLoginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "members/member_login";
    }

    /**
     * 로그인 기능
     */
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "members/member_login";
        }

        log.info("로그인 아이디={}", loginForm.getLoginId());
        log.info("로그인 패스워드={}", loginForm.getLoginId());


        // 성공시 로그인 기능 적용후 멤버에 저장 틀릴시 예외처리
        MemberDTO loginMember = memberService.login(loginForm.getLoginId(), loginForm.getPassword()); // 폼에 입력한 아이디 패스워드 가져와서 멤버로 저장
        log.info("login? {}", loginMember);


        // 로그인 성공 처리
        // HttpSession 객체에  request.getSession()로 담으면 됨
        // 세션이 있으면 있는 세션을 반환하고 없으면 신규 세션을 생성한다. (true일 때)
        HttpSession session = request.getSession(); // 기본 값이 true이고, false는 없으면 생성 안함
        // 세션에 로그인 회원 정보를 보관한다.
        session.setAttribute(LoginSessionConst.LOGIN_MEMBER, loginMember);

        log.info("sessionId={}", session.getId());
        return "redirect:/";
    }

    /**
     * 로그아웃 기능
     */
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) { // SessionManager에서 request로 사용
        // request.getSession( ) 안에 false는 현재 세션이 있으면 기존 세션 반환, 세션이 없으면 null을 반환
        //                            true는 현재 세션이 있으면 기존 세션 반환, 세션이 없으면 새로운 세션 생성
        HttpSession session = request.getSession(false); // 로그아웃은 없으면 새로운 세션을 생성할 필요 없기에 false를 넣음

        if (session != null) {    // 세션이 있으면
            session.invalidate(); // 해당 세션이랑 그 안의 데이터를 모두 지운다.
            log.info("로그아웃 완료");
        }

        return "redirect:/";
    }


    /**
     * 회원 수정 폼
     */
    @GetMapping("/{memberId}/update")
    public String memberUpdateForm(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER) MemberDTO loginMember,
                                   @ModelAttribute("updateForm") UpdateForm form,
                                   @PathVariable Long memberId,
                                   Model model) {
        // 세션 회원 검증
        if (loginMember == null) {
            model.addAttribute("message", "회원만 이용할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        // 본인 프로필인지 검증
        if (loginMember.getId() != memberId) {
            model.addAttribute("message", "본인 프로필이 아닙니다.");
            model.addAttribute("redirectUrl", "/");
            return "messages";
        }

        form.setName(loginMember.getName());
        form.setPassword(memberService.findMemberDtoById(memberId).getPassword());

        log.info("기존 이름 = {}", form.getName());
        log.info("기존 비밀번호 = {}", form.getPassword());

        return "members/member_update";
    }

    /**
     * 회원 수정 기능
     */
    @PostMapping("/{memberId}/update")
    public String memberUpdate(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER) MemberDTO loginMember,
                               @Valid @ModelAttribute("updateForm") UpdateForm form,
                               BindingResult bindingResult,
                               @PathVariable Long memberId, Model model,
                               HttpServletRequest request) throws IOException {
        if (bindingResult.hasErrors()) {
            return "members/member_update";
        }

        log.info("받아온 이름 = {}", form.getName());
        log.info("받아온 비밀번호 = {}", form.getPassword());

        memberService.changeProfile(loginMember, form);

        // 프로필 사진 갱신을 위해 로그아웃
        HttpSession session = request.getSession(false);
        if (session != null) { 
            session.invalidate();
            log.info("로그아웃 완료");
        }
        
        // 다시 로그인 처리
        HttpSession newSession = request.getSession();
        newSession.setAttribute(LoginSessionConst.LOGIN_MEMBER, loginMember);

        model.addAttribute("message", "수정 되었습니다!");
        model.addAttribute("redirectUrl", "/members/" + memberId + "/update");
        return "messages";
    }

}
