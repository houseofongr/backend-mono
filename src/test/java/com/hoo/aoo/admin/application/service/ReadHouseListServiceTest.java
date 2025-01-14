package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.application.port.in.ReadHouseListResult;
import com.hoo.aoo.admin.application.port.out.database.QueryHousePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReadHouseListServiceTest {

    ReadHouseListService sut;

    QueryHousePort queryHousePort;

    @BeforeEach
    void init() {
        queryHousePort = mock();
        sut = new ReadHouseListService(queryHousePort);
    }

    @Test
    @DisplayName("리스트 조회 서비스 테스트")
    void testGetListService() {
        // given
        List<HouseJpaEntity> es = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            HouseJpaEntity e = new HouseJpaEntity((long) i, "test house", "leaf", "create test house", 5000, 5000, 1L, 2L, null);
            e.prePersist();
            es.add(e);
        }
        Page<HouseJpaEntity> entities= new PageImpl<>(es);

        // when
        when(queryHousePort.query(any())).thenReturn(entities);
        ReadHouseListResult list = sut.getList(any());

        // then
        assertThat(list.houses()).hasSize(9);
    }
}