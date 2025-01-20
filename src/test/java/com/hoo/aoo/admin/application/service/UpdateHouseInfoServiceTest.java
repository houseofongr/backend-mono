package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.house.UpdateHouseInfoCommand;
import com.hoo.aoo.admin.application.port.in.house.UpdateRoomInfoCommand;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.admin.application.port.out.house.UpdateHousePort;
import com.hoo.aoo.admin.application.port.out.house.UpdateRoomPort;
import com.hoo.aoo.admin.application.service.house.UpdateHouseService;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.hoo.aoo.common.FixtureRepository.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateHouseInfoServiceTest {

    UpdateHouseService sut;

    FindHousePort findHousePort;
    UpdateHousePort updateHousePort;
    UpdateRoomPort updateRoomPort;

    @BeforeEach
    void init() {
        findHousePort = mock();
        updateHousePort = mock();
        updateRoomPort = mock();
        sut = new UpdateHouseService(findHousePort, updateHousePort, updateRoomPort);
    }

    @Test
    @DisplayName("하우스 업데이트 서비스 테스트")
    void testUpdateHouseInfo() throws Exception {
        // given
        House house = getHouse(new HouseId("cozy house", "leaf", "this is cozy house"), List.of());
        UpdateHouseInfoCommand command = new UpdateHouseInfoCommand(1L, "not cozy house", "arang", "this is not cozy house.");

        // when
        when(findHousePort.find(1L)).thenReturn(Optional.of(house));
        MessageDto message = sut.update(command);

        // then
        verify(findHousePort, times(1)).find(1L);
        verify(updateHousePort, times(1)).update(any(),any());

        assertThat(message.message()).isEqualTo("1번 하우스 정보 수정이 완료되었습니다.");
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