package com.kyk.HotSpace.exception.member;

/**
 * 중복 관련 예외
 */
public class DuplicatedException extends MemberException {
    public DuplicatedException(String message) {
        super(message);
    }
}
