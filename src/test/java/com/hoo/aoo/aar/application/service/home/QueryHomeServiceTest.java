package com.hoo.aoo.aar.application.service.home;

import com.hoo.aoo.aar.application.port.out.home.QueryHomePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class QueryHomeServiceTest {

    QueryHomeService sut;

    QueryHomePort queryHomePort;

    @BeforeEach
    void init() {
        queryHomePort = mock();
        sut = new QueryHomeService(queryHomePort);
    }

    @Test
    @DisplayName("유저 홈 조회 서비스 테스트")
    void testQueryUserHomes() {
        // given
        Long userId = 1L;

        // when
        sut.queryUserHomes(userId);

        // then
        verify(queryHomePort, times(1)).queryUserHome(userId);
    }

}