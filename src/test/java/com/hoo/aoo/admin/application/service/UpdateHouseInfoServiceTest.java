package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.UpdateHouseInfoCommand;
import com.hoo.aoo.admin.application.port.out.LoadHousePort;
import com.hoo.aoo.admin.application.port.out.database.UpdateHousePort;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.hoo.aoo.common.FixtureRepository.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UpdateHouseInfoServiceTest {

    UpdateHouseInfoService sut;

    LoadHousePort loadHousePort;
    UpdateHousePort updateHousePort;

    @BeforeEach
    void init() {
        loadHousePort = mock();
        updateHousePort = mock();
        sut = new UpdateHouseInfoService(loadHousePort, updateHousePort);
    }

    @Test
    @DisplayName("하우스 업데이트 서비스 테스트")
    void testUpdateInfo() throws Exception {
        // given
        House house = getHouse(new HouseId("cozy house", "leaf", "this is cozy house"), List.of());
        UpdateHouseInfoCommand command = new UpdateHouseInfoCommand(1L, "not cozy house", "arang", "this is not cozy house.");

        // when
        when(loadHousePort.load(1L)).thenReturn(Optional.of(house));
        MessageDto message = sut.update(command);

        // then
        verify(loadHousePort, times(1)).load(1L);
        verify(updateHousePort, times(1)).update(any(),any());

        assertThat(message.message()).isEqualTo("1번 하우스 정보 수정이 완료되었습니다.");
    }


}