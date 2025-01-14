package com.hoo.aoo.admin.domain.room;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.HouseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomTest {

    @Test
    @DisplayName("룸 생성 테스트")
    void testCreateRoom() throws AxisLimitExceededException, AreaLimitExceededException {
        // given
        HouseId houseId = new HouseId("cozy house", "leaf", "this is cozy house");
        String name = "거실";
        Float x = 123f;
        Float y = 456f;
        Float z = 1f;
        Integer width = 100;
        Integer height = 200;
        Long imageId = 1L;

        // when
        Room newRoom = Room.create(houseId, name, x, y, z, width, height, imageId);

        // then
        assertThat(newRoom.getId().getHouseId().getTitle()).isEqualTo("cozy house");
        assertThat(newRoom.getId().getName()).isEqualTo("거실");
        assertThat(newRoom.getAxis().getX()).isEqualTo(123);
        assertThat(newRoom.getAxis().getY()).isEqualTo(456);
        assertThat(newRoom.getAxis().getZ()).isEqualTo(1);
        assertThat(newRoom.getArea().getWidth()).isEqualTo(100);
        assertThat(newRoom.getArea().getHeight()).isEqualTo(200);
        assertThat(newRoom.getImage().getImageId()).isEqualTo(1L);

    }
}