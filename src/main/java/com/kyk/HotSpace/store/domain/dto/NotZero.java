package com.kyk.HotSpace.store.domain.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 커스텀 검증 어노테이션 적용해보았지만,
 * 복잡성이 늘어 특정한 경우에만 사용하도록 하기
 */
@Constraint(validatedBy = NotZeroValidator.class) // 이 어노테이션이 유효성 검사를 수행하도록 정의하고, 검증 로직이 들어 있는 클래스를 지정
@Target({ElementType.FIELD})                      // 이 어노테이션이 클래스의 필드에 적용될 수 있음을 지정
@Retention(RetentionPolicy.RUNTIME)               // 이 어노테이션이 런타임까지 유지되어야 하며, 런타임 시에 사용할 수 있음을 의미
public @interface NotZero {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}