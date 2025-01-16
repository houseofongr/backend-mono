package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.ReadHouseListResult;
import com.hoo.aoo.admin.application.port.in.ReadHouseResult;
import com.hoo.aoo.admin.application.port.out.QueryHousePort;
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

class ReadHouseServiceTest {

    findHouseService sut;

    QueryHousePort queryHousePort;

    @BeforeEach
    void init() {
        queryHousePort = mock();
        sut = new findHouseService(queryHousePort);
    }

    @Test
    @DisplayName("리스트 조회 서비스 테스트")
    void testGetListService() {
        // given
        List<HouseJpaEntity> es = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            HouseJpaEntity e = new HouseJpaEntity((long) i, "test house", "leaf", "create test house", 5000F, 5000F, 1L, 2L, null);
            e.prePersist();
            es.add(e);
        }
        Page<HouseJpaEntity> entities= new PageImpl<>(es);

        // when
        when(queryHousePort.pageQuery(any())).thenReturn(entities);
        ReadHouseListResult list = sut.getList(any());

        // then
        assertThat(list.houses()).hasSize(9);
    }

    @Test
    @DisplayName("단건 조회 서비스 테스트")
    void testGetService() {
        // given
        List<RoomJpaEntity> rooms = List.of(
                new RoomJpaEntity(1L, "거실", 0f, 0f, 0f, 5000f, 1000f, 3L, null),
                new RoomJpaEntity(2L, "주방", 0f, 1000f, 0f, 5000f, 1000f, 4L, null)
        );
        HouseJpaEntity entity = new HouseJpaEntity(1L, "cozy house", "leaf", "this is cozy house", 5000f, 5000f, 1L, 2L, rooms);
        rooms.forEach(roomJpaEntity -> roomJpaEntity.setHouse(entity));
        entity.prePersist();

        // when

        when(queryHousePort.query(1L)).thenReturn(Optional.of(entity));
        ReadHouseResult result = sut.get(1L);

        // then
        assertThat(result).isEqualTo(ReadHouseResult.of(entity));

        // 조회되지 않을 때 예외처리
        assertThatThrownBy(() -> sut.get(2L)).isInstanceOf(AdminException.class)
                        .hasMessage(AdminErrorCode.HOUSE_NOT_FOUND.getMessage());
    }
}