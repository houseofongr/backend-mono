package com.hoo.aoo.admin.application.service.house;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListResult;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseResult;
import com.hoo.aoo.admin.application.port.out.house.FindHousePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.common.application.port.in.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueryHouseServiceTest {

    QueryHouseService sut;

    FindHousePort findHousePort;

    @BeforeEach
    void init() {
        findHousePort = mock();
        sut = new QueryHouseService(findHousePort);
    }

    @Test
    @DisplayName("하우스 리스트 조회 서비스 테스트")
    void testQueryHouseListService() {
        // given

        QueryHouseListResult result = new QueryHouseListResult(List.of(
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null),
                new QueryHouseListResult.HouseInfo(null, null, null, null, null, null, null)
        ), new Pagination(1, 1, 1, 1L));

        // when
        when(findHousePort.search(any())).thenReturn(result);
        QueryHouseListResult list = sut.query(any());

        // then
        assertThat(list.houses()).hasSize(9);
    }

    @Test
    @DisplayName("하우스 단건 조회 서비스 테스트")
    void testQueryHouseService() {
        // given
        List<RoomJpaEntity> rooms = List.of(
                new RoomJpaEntity(1L, "거실", 0f, 0f, 0f, 5000f, 1000f, 3L, null, null, null, null),
                new RoomJpaEntity(2L, "주방", 0f, 1000f, 0f, 5000f, 1000f, 4L, null, null, null, null)
        );
        HouseJpaEntity entity = new HouseJpaEntity(1L, "cozy house", "leaf", "this is cozy house", 5000f, 5000f, 1L, 2L, rooms);
        rooms.forEach(roomJpaEntity -> roomJpaEntity.setHouse(entity));
        entity.prePersist();

        QueryHouseResult queryHouseResult = new HouseMapper().mapToQueryHouseResult(entity);

        // when
        when(findHousePort.findResult(1L)).thenReturn(Optional.of(queryHouseResult));
        QueryHouseResult result = sut.queryHouse(1L);

        // then
        assertThat(result).isNotNull();

        // 조회되지 않을 때 예외처리
        assertThatThrownBy(() -> sut.queryHouse(2L)).isInstanceOf(AdminException.class)
                .hasMessage(AdminErrorCode.HOUSE_NOT_FOUND.getMessage());
    }

}