package com.hoo.aoo.admin.application.service.room;

import com.hoo.aoo.admin.application.port.in.room.QueryRoomResult;
import com.hoo.aoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class QueryRoomServiceTest {

    QueryRoomService sut;

    FindRoomPort findRoomPort;

    @BeforeEach
    void init() {
        findRoomPort = mock();
        sut = new QueryRoomService(findRoomPort);
    }

    @Test
    @DisplayName("룸 조회 서비스 테스트")
    void testQueryRoomService() throws Exception {
        // given
        Long id = 1L;

        // when
        when(findRoomPort.load(1L)).thenReturn(Optional.of(MockEntityFactoryService.getRoom()));
        QueryRoomResult result = sut.queryRoom(id);

        // then
        assertThat(result).isNotNull();
    }

}