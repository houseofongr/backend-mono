package com.hoo.aoo.admin.application.service.room;

import com.hoo.aoo.admin.application.port.in.room.UpdateRoomInfoCommand;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.admin.application.port.out.house.UpdateHousePort;
import com.hoo.aoo.admin.application.port.out.room.UpdateRoomPort;
import com.hoo.aoo.admin.application.service.house.UpdateHouseService;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.hoo.aoo.common.FixtureRepository.getHouseWithRoom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateRoomServiceTest {

    UpdateRoomService sut;

    UpdateRoomPort updateRoomPort;

    @BeforeEach
    void init() {
        updateRoomPort = mock();
        sut = new UpdateRoomService(updateRoomPort);
    }

    @Test
    @DisplayName("룸 업데이트 서비스 테스트")
    void testUpdateRoomInfo() throws Exception{
        // given
        House houseWithRoom = getHouseWithRoom();
        UpdateRoomInfoCommand command = new UpdateRoomInfoCommand(List.of(new UpdateRoomInfoCommand.RoomInfo(1L, "욕실")));
        UpdateRoomInfoCommand notFoundCommand = new UpdateRoomInfoCommand(List.of(new UpdateRoomInfoCommand.RoomInfo(100L, "욕실")));

        // when
        when(updateRoomPort.update(command)).thenReturn(1);
        MessageDto message = sut.update(command);

        // then
        verify(updateRoomPort, times(1)).update(any());

        assertThat(message.message()).isEqualTo("1개 룸의 정보 수정이 완료되었습니다.");
        assertThat(sut.update(notFoundCommand).message()).isEqualTo("0개 룸의 정보 수정이 완료되었습니다.");
    }

}