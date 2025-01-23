package com.hoo.aoo.common.application.service;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.Detail;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.common.application.port.out.IssueIdPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EntityFactoryServiceTest {

    EntityFactoryService sut;
    IssueIdPort issueIdPort;

    @BeforeEach
    void init() {
        issueIdPort = mock();
        sut = new EntityFactoryService(issueIdPort);
    }

    @Test
    @DisplayName("하우스 생성 테스트")
    void testCreateHouse() throws AreaLimitExceededException {
        // given
        Detail detail = new Detail("title", "author", "description");

        // when
        when(issueIdPort.issueHouseId()).thenReturn(1L);
        House house = sut.createHouse(detail, 5000f, 5000f, 1L, 1L, List.of());

        // then
        assertThat(house).isNotNull();
        assertThat(house.getHouseId().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("룸 생성 테스트")
    void testCreateRoom() throws AxisLimitExceededException, AreaLimitExceededException {
        // given

        // when
        when(issueIdPort.issueRoomId()).thenReturn(1L);
        Room room = sut.createRoom("name", 0f, 0f, 0f, 1f, 1f, 1L);

        // then
        assertThat(room).isNotNull();
        assertThat(room.getRoomId().getId()).isEqualTo(1L);
    }
}