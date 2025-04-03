package com.hoo.aoo.admin.domain.universe;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class DateInfo {
    private final ZonedDateTime createdDate;
    private final ZonedDateTime updatedDate;

    public DateInfo(ZonedDateTime createdDate, ZonedDateTime updatedDate) {
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
