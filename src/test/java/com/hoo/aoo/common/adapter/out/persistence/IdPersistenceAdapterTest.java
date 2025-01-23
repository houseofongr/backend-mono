package com.hoo.aoo.common.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.RoomJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import(IdPersistenceAdapter.class)
class IdPersistenceAdapterTest {

    @Autowired
    IdPersistenceAdapter sut;

    @Autowired
    HouseJpaRepository houseJpaRepository;

    @Autowired
    RoomJpaRepository roomJpaRepository;

    @Test
    @Sql("IdPersistenceAdapterTest.sql")
    @DisplayName("ID 생성 테스트")
    void testCreateHouseId() {
        long houseCount = houseJpaRepository.count();
        long roomCount = roomJpaRepository.count();

        // when
        Long houseId = sut.issueHouseId();
        Long roomId = sut.issueRoomId();

        // then
        assertThat(houseId).isEqualTo(houseCount + 1);
        assertThat(roomId).isEqualTo(roomCount + 1);
    }

}