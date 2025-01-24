package com.hoo.aoo.common.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.ItemJpaRepository;
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

    @Autowired
    HomeJpaRepository homeJpaRepository;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Test
    @Sql("IdPersistenceAdapterTest.sql")
    @DisplayName("ID 생성 테스트")
    void testCreateId() {
        long houseCount = houseJpaRepository.count();
        long roomCount = roomJpaRepository.count();
        long homeCount = homeJpaRepository.count();
        long itemCount = itemJpaRepository.count();

        System.out.println(itemJpaRepository.findAll());

        // when
        Long houseId = sut.issueHouseId();
        Long roomId = sut.issueRoomId();
        Long homeId = sut.issueHomeId();
        Long itemId = sut.issueItemId();

        // then
        assertThat(houseId).isEqualTo(houseCount + 1);
        assertThat(roomId).isEqualTo(roomCount + 1);
        assertThat(homeId).isEqualTo(homeCount + 1);
        assertThat(itemId).isEqualTo(itemCount + 1);
    }

}