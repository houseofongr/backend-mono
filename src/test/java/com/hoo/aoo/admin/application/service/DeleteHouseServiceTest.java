package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.out.DeleteHousePort;
import com.hoo.aoo.admin.application.port.out.DeleteRoomPort;
import com.hoo.aoo.admin.application.port.out.FindHousePort;
import com.hoo.aoo.admin.application.port.out.FindRoomPort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import jakarta.persistence.Id;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteHouseServiceTest {

    DeleteHouseService sut;

    FindHousePort findHousePort;
    FindRoomPort findRoomPort;
    DeleteHousePort deleteHousePort;
    DeleteRoomPort deleteRoomPort;

    @BeforeEach
    void init() {
        findHousePort = mock();
        findRoomPort = mock();
        deleteHousePort = mock();
        deleteRoomPort = mock();
        sut = new DeleteHouseService(findHousePort, findRoomPort, deleteHousePort, deleteRoomPort);
    }

    @Test
    @DisplayName("하우스 삭제 서비스 테스트")
    void testDeleteHouse() {
        // given
        Long id = 1L;

        // when
        MessageDto messageDto = sut.delete(id);

        // then
        verify(deleteHousePort, times(1)).delete(id);
        assertThat(messageDto.message()).isEqualTo("1번 하우스가 삭제되었습니다.");
    }

    @Test
    @DisplayName("룸 삭제 서비스 테스트")
    void testDeleteRoom() {
        // given
        Long id = 1L;
        String name = "거실";

        // when
        MessageDto messageDto = sut.delete(id, name);

        // then
        verify(deleteRoomPort, times(1)).delete(id, name);
        assertThat(messageDto.message()).isEqualTo("1번 하우스 거실 룸이 삭제되었습니다.");
    }

}