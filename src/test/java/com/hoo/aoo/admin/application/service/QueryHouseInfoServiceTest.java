package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.house.*;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.admin.application.port.out.house.FindRoomPort;
import com.hoo.aoo.admin.application.port.out.house.SearchHousePort;
import com.hoo.aoo.admin.application.service.house.QueryHouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueryHouseInfoServiceTest {

    QueryHouseService sut;

    SearchHousePort searchHousePort;
    FindHousePort findHousePort;
    FindRoomPort findRoomPort;

    @BeforeEach
    void init() {
        searchHousePort = mock();
        findHousePort = mock();
        findRoomPort = mock();
        sut = new QueryHouseService(searchHousePort, findHousePort, findRoomPort);
    }

    @Test
    @DisplayName("하우스 리스트 조회 서비스 테스트")
    void testQueryHouseListService() {
        // given
        List<HouseJpaEntity> es = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            HouseJpaEntity e = new HouseJpaEntity((long) i, "test house", "leaf", "create test house", 5000F, 5000F, 1L, 2L, null);
            e.prePersist();
            es.add(e);
        }
        Page<HouseJpaEntity> entities= new PageImpl<>(es);

        // when
        when(searchHousePort.search(any())).thenReturn(entities);
        QueryHouseListResult list = sut.query((QueryHouseListCommand) any());

        // then
        assertThat(list.houses()).hasSize(9);
    }

    @Test
    @DisplayName("하우스 단건 조회 서비스 테스트")
    void testQueryHouseService() {
        // given
        List<RoomJpaEntity> rooms = List.of(
                new RoomJpaEntity(1L, "거실", 0f, 0f, 0f, 5000f, 1000f, 3L, null),
                new RoomJpaEntity(2L, "주방", 0f, 1000f, 0f, 5000f, 1000f, 4L, null)
        );
        HouseJpaEntity entity = new HouseJpaEntity(1L, "cozy house", "leaf", "this is cozy house", 5000f, 5000f, 1L, 2L, rooms);
        rooms.forEach(roomJpaEntity -> roomJpaEntity.setHouse(entity));
        entity.prePersist();

        // when
        when(findHousePort.findHouseJpaEntity(1L)).thenReturn(Optional.of(entity));
        QueryHouseResult result = sut.queryHouse(1L);

        // then
        assertThat(result).isEqualTo(QueryHouseResult.of(entity));

        // 조회되지 않을 때 예외처리
        assertThatThrownBy(() -> sut.queryHouse(2L)).isInstanceOf(AdminException.class)
                        .hasMessage(AdminErrorCode.HOUSE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("룸 조회 서비스 테스트")
    void testQueryRoomService() {
        // given
        RoomJpaEntity entity = new RoomJpaEntity(1L, "거실", 0f, 0f, 0f, 1000f, 1000f, 1L, null);

        // when
        when(findRoomPort.findRoomJpaEntity(1L)).thenReturn(Optional.of(entity));
        QueryRoomResult result = sut.queryRoom(1L);

        // then
        assertThat(result.room().name()).isEqualTo("거실");
        assertThat(result.room().width()).isEqualTo(1000f);
        assertThat(result.room().height()).isEqualTo(1000f);
        assertThat(result.room().imageId()).isEqualTo(1L);
    }
}