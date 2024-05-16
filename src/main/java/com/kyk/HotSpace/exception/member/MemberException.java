package com.kyk.HotSpace.exception.member;

/**
 * 회원 관련 예외의 제일 상위 계층인 MemberException 생성
 */
public class MemberException extends RuntimeException{
    public MemberException() {
        super();
    }

    public MemberException(String message) {
        super(message);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberException(Throwable cause) {
        super(cause);
    }

    protected MemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
