package com.hoo.aar.adapter.out.persistence.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

class DateColumnBaseEntityTest {

    @Test
    @DisplayName("현재 시스템 시간 확인")
    void testCurrentSystemTime() {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("zoneId = " + zoneId);
        System.out.println("now = " + now);
    }
}