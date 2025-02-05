package com.hoo.aoo.aar.application.service.user;

import com.hoo.aoo.aar.application.port.out.user.FindUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueryMyInfoServiceTest {

    QueryMyInfoService sut;

    FindUserPort findUserPort;

    @BeforeEach
    void init() {
        findUserPort = mock();
        sut = new QueryMyInfoService(findUserPort);
    }

    @Test
    @DisplayName("본인정보 조회 서비스 테스트")
    void testQueryMyInfoService() {
        // given
        Long userId = 1L;

        // when
        sut.queryMyInfo(userId);

        // then
        verify(findUserPort, times(1)).queryMyInfo(userId);
    }
}