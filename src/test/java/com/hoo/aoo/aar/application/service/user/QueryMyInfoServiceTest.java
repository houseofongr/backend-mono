package com.hoo.aoo.aar.application.service.user;

import com.hoo.aoo.aar.application.port.out.persistence.user.QueryUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class QueryMyInfoServiceTest {

    QueryMyInfoService sut;

    QueryUserPort queryUserPort;

    @BeforeEach
    void init() {
        queryUserPort = mock();
        sut = new QueryMyInfoService(queryUserPort);
    }

    @Test
    @DisplayName("본인정보 조회 서비스 테스트")
    void testQueryMyInfoService() {
        // given
        Long userId = 1L;

        // when
        sut.queryMyInfo(userId);

        // then
        verify(queryUserPort, times(1)).queryMyInfo(userId);
    }
}