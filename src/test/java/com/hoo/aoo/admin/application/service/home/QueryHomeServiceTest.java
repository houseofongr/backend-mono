package com.hoo.aoo.admin.application.service.home;

import com.hoo.aoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.aoo.admin.application.port.out.home.FindHomePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

class QueryHomeServiceTest {

    QueryHomeService sut;

    FindHomePort findHomePort;

    @BeforeEach
    void init() {
        findHomePort = mock();
        sut = new QueryHomeService(findHomePort);
    }

    @Test
    @DisplayName("홈 조회 서비스 테스트")
    void testQueryHome() {
        // given
        Long id = 1L;

        // when
        when(findHomePort.findHome(1L)).thenReturn(Optional.of(new QueryHomeResult(null,null,null,null,null,null)));
        QueryHomeResult queryHomeResult = sut.queryHome(id);

        // then
        verify(findHomePort, times(1)).findHome(1L);
        assertThat(queryHomeResult).isNotNull();
        assertThatThrownBy(() -> sut.queryHome(2L)).hasMessage(AdminErrorCode.HOME_NOT_FOUND.getMessage());
    }

}