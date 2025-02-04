package com.hoo.aoo.aar.application.service.home;

import com.hoo.aoo.aar.application.port.out.home.CheckOwnerPort;
import com.hoo.aoo.aar.application.port.out.home.QueryHomePort;
import com.hoo.aoo.aar.application.service.AarErrorCode;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueryHomeServiceTest {

    QueryHomeService sut;

    CheckOwnerPort checkOwnerPort;
    QueryHomePort queryHomePort;

    @BeforeEach
    void init() {
        checkOwnerPort = mock();
        queryHomePort = mock();
        sut = new QueryHomeService(checkOwnerPort, queryHomePort);
    }

    @Test
    @DisplayName("유저 홈 조회 서비스 테스트")
    void testQueryUserHomes() {
        // given
        Long userId = 1L;

        // when
        sut.queryUserHomes(userId);

        // then
        verify(queryHomePort, times(1)).queryUserHomes(userId);
    }

    @Test
    @DisplayName("홈 룸 조회 서비스 테스트")
    void testQueryHomeRooms() {
        // given
        Long userId = 10L;
        Long homeId = 1L;

        // when
        when(checkOwnerPort.checkHome(userId,homeId)).thenReturn(true);
        assertThatThrownBy(() -> sut.queryHomeRooms(userId,123L)).hasMessage(AarErrorCode.NOT_OWNED_HOME.getMessage());
        sut.queryHomeRooms(userId,homeId);

        // then
        verify(queryHomePort, times(1)).queryHomeRooms(homeId);
    }

}