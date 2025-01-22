package com.hoo.aoo.admin.adapter.out.persistence.mapper;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseResult;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HouseMapperTest {

    HouseMapper sut = new HouseMapper();

    @Test
    @DisplayName("하우스 쿼리 매핑 테스트")
    void testMapToQueryHouse() {
        // given
        List<RoomJpaEntity> rooms = List.of(
                new RoomJpaEntity(1L, "거실", 0f, 0f, 0f, 5000f, 1000f, 3L, null),
                new RoomJpaEntity(2L, "주방", 0f, 1000f, 0f, 5000f, 1000f, 4L, null)
        );
        HouseJpaEntity entity = new HouseJpaEntity(1L, "cozy house", "leaf", "this is cozy house", 5000f, 5000f, 1L, 2L, rooms);
        rooms.forEach(roomJpaEntity -> roomJpaEntity.setHouse(entity));

        // when
        entity.prePersist();
        QueryHouseResult result = sut.mapToQueryHouseResult(entity);

        // then
        assertThat(result.house().title()).isEqualTo("cozy house");
        assertThat(result.house().author()).isEqualTo("leaf");
        assertThat(result.house().description()).isEqualTo("this is cozy house");
        assertThat(result.house().width()).isEqualTo(5000f);
        assertThat(result.house().height()).isEqualTo(5000f);
        assertThat(result.house().borderImageId()).isEqualTo(2L);

        DateTimeFormatter formatter = DateTimeFormatters.ENGLISH_DATE.getFormatter();
        assertThat(result.house().createdDate()).isEqualTo(formatter.format(entity.getCreatedTime()));
        assertThat(result.house().updatedDate()).isEqualTo(formatter.format(entity.getUpdatedTime()));

        // room
        assertThat(result.rooms()).anySatisfy(room -> {
            assertThat(room.name()).isEqualTo("거실");
            assertThat(room.x()).isEqualTo(0);
            assertThat(room.y()).isEqualTo(0);
            assertThat(room.z()).isEqualTo(0);
            assertThat(room.width()).isEqualTo(5000);
            assertThat(room.height()).isEqualTo(1000);
            assertThat(room.imageId()).isEqualTo(3);
        });
    }
}