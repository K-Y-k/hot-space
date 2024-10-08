package com.kyk.HotSpace.exception.exhandler;

import com.kyk.HotSpace.exception.member.DuplicatedException;
import com.kyk.HotSpace.exception.member.MemberException;
import com.kyk.HotSpace.exception.member.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 여러 컨트롤러에서 발생하는
 * 예외에 대한 처리를 공통으로 해주는 곳 (ControllerAdvice로 분리)
 *
 * @ControllerAdvice 예외 처리 전용
 */
@Slf4j
@ControllerAdvice
public class ExControllerAdvice {
    /**
     * 회원가입시 중복된 닉네임 또는 로그인 ID일 시 예외처리
     */
    @ExceptionHandler(value = DuplicatedException.class)
    public ModelAndView DuplicatedMemberExHandler(MemberException ex) {
        log.error("[" + ex.getClass() + "] ex", ex);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("redirectUrl", "/members/join");
        modelAndView.setViewName("messages");

        return modelAndView;
    }


    /**
     * 로그인시 아이디 또는 패스워드 불일치 예외처리
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ModelAndView MemberNotFoundExHandler(MemberNotFoundException ex, HttpServletRequest request) {
        log.error("[" + ex.getClass() + "] ex", ex);

        ModelAndView modelAndView = new ModelAndView();

        String requestURL = String.valueOf(request.getRequestURL());
        log.info("requestURL={}", requestURL);


        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("redirectUrl", "/members/login");
        modelAndView.setViewName("messages");

        return modelAndView;
    }


    /**
     * 최상위 Exception 예외처리
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView ExceptionExHandler(Exception ex, HttpServletRequest request) {
        log.error("[" + ex.getClass() + "] ex", ex);

        ModelAndView modelAndView = new ModelAndView();

        String requestURL = String.valueOf(request.getRequestURL());
        log.info("requestURL={}", requestURL);


        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("redirectUrl", "/");
        modelAndView.setViewName("messages");

        return modelAndView;
    }
}
