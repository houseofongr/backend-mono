package com.hoo.aoo.admin.domain.room;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.Axis;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.HouseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomTest {

    @Test
    @DisplayName("룸 생성 테스트")
    void testCreateRoom() throws AxisLimitExceededException, AreaLimitExceededException {
        // given
        String title = "cozy house";
        String name = "거실";
        Integer x = 123;
        Integer y = 456;
        Integer z = 1;
        Integer width = 100;
        Integer height = 200;
        Long imageId = 1L;

        // when
        HouseId houseId = new HouseId(title);
        RoomId roomId = new RoomId(houseId, name);
        Axis axis = new Axis(x, y, z);
        Area area = new Area(width, height);
        RoomImage image = new RoomImage(imageId);

        // then
        Room.createNewRoom(roomId, axis, area, image);
    }
}