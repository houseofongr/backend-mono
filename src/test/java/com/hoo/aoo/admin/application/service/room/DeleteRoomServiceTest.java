package com.hoo.aoo.admin.application.service.room;

import com.hoo.aoo.admin.application.port.out.room.DeleteRoomPort;
import com.hoo.aoo.common.application.port.in.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class DeleteRoomServiceTest {

    DeleteRoomService sut;

    DeleteRoomPort deleteRoomPort;

    @BeforeEach
    void init() {
        deleteRoomPort = mock();
        sut = new DeleteRoomService(deleteRoomPort);
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