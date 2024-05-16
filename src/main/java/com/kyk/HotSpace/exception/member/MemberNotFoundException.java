package com.kyk.HotSpace.exception.member;

/**
 * 회원 찾기 실패 예외
 */
public class MemberNotFoundException extends MemberException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
