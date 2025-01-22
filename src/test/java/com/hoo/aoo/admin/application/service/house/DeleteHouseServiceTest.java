package com.hoo.aoo.admin.application.service.house;

import com.hoo.aoo.admin.application.port.out.house.DeleteHousePort;
import com.hoo.aoo.admin.application.port.out.room.DeleteRoomPort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeleteHouseServiceTest {

    DeleteHouseService sut;

    DeleteHousePort deleteHousePort;

    @BeforeEach
    void init() {
        deleteHousePort = mock();
        sut = new DeleteHouseService(deleteHousePort);
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

}