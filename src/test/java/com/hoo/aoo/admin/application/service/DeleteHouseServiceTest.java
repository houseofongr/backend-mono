package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.out.DeleteHousePort;
import com.hoo.aoo.admin.application.port.out.DeleteRoomPort;
import com.hoo.aoo.admin.application.port.out.FindHousePort;
import com.hoo.aoo.admin.application.port.out.FindRoomPort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteHouseServiceTest {

    DeleteHouseService sut;

    DeleteHousePort deleteHousePort;
    DeleteRoomPort deleteRoomPort;

    @BeforeEach
    void init() {
        deleteHousePort = mock();
        deleteRoomPort = mock();
        sut = new DeleteHouseService(deleteHousePort, deleteRoomPort);
    }

    @Test
    @DisplayName("하우스 삭제 서비스 테스트")
    void testDeleteHouseHouse() {
        // given
        Long id = 1L;

        // when
        MessageDto messageDto = sut.deleteHouse(id);

        // then
        verify(deleteHousePort, times(1)).deleteHouse(id);
        assertThat(messageDto.message()).isEqualTo("1번 하우스가 삭제되었습니다.");
    }

    @Test
    @DisplayName("룸 삭제 서비스 테스트")
    void testDeleteRoomRoom() {
        // given
        Long id = 1L;

        // when
        MessageDto messageDto = sut.deleteRoom(id);

        // then
        verify(deleteRoomPort, times(1)).deleteRoom(id);
        assertThat(messageDto.message()).isEqualTo("1번 룸이 삭제되었습니다.");
    }

}