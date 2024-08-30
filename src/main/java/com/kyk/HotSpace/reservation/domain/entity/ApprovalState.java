package com.kyk.HotSpace.reservation.domain.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApprovalState {
    STAND("ApprovalState_STAND", "대기"),
    APPROVE("ApprovalState_APPROVE", "승인"),
    REJECT("ApprovalState_REJECT", "거절");

    private final String key;
    private final String title;

    @JsonValue
    public String getTitle() {
        return title;
    }
}
