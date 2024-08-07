package com.kyk.HotSpace.store.domain.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 검증 로직을 구현한 클래스
 */
public class NotZeroValidator implements ConstraintValidator<NotZero, Double> {

    @Override
    public void initialize(NotZero constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        // 값이 0.0이 아닌 경우에만 유효
        return value != 0.0;
    }
}