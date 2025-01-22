package com.hoo.aoo.admin.application.service.room;

import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.room.QueryRoomResult;
import com.hoo.aoo.admin.application.port.out.room.FindRoomPort;
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
    void testQueryRoomService() {
        // given
        Long id = 1L;

        // when
        when(findRoomPort.findResult(1L)).thenReturn(Optional.of(new QueryRoomResult(null)));
        QueryRoomResult result = sut.queryRoom(id);

        // then
        verify(findRoomPort, times(1)).findResult(1L);
        assertThat(result).isNotNull();
    }

}