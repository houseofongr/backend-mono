package com.hoo.aoo.aar.domain;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class DateInfo {
    private final ZonedDateTime createdTime;
    private final ZonedDateTime updatedTime;

    public DateInfo(ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
