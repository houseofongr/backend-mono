package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.Area;
import com.hoo.aoo.admin.domain.Axis;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.admin.domain.room.RoomId;
import com.hoo.aoo.admin.domain.room.RoomImage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HouseTest {

    @Test
    @DisplayName("집 생성 테스트")
    void testCreateHouse() throws AreaLimitExceededException, AxisLimitExceededException {
        // given
        String title = "cozy house";
        String author = "leaf";
        Integer width = 5000;
        Integer height = 5000;
        Long basicImageId = 1L;
        Long borderImageId = 1L;

        String name = "거실";
        Integer x = 123;
        Integer y = 456;
        Integer z = 1;
        Integer roomWidth = 100;
        Integer roomHeight = 200;
        Long imageId = 1L;

        // when
        HouseId houseId = new HouseId(title);
        Author author_ = new Author(author);
        Area area = new Area(width, height);
        HouseImages houseImages = new HouseImages(basicImageId, borderImageId);

        RoomId roomId = new RoomId(houseId, name);
        Axis axis = new Axis(x, y, z);
        Area roomArea = new Area(roomWidth, roomHeight);
        RoomImage image = new RoomImage(imageId);

        List<Room> rooms = List.of(Room.createNewRoom(roomId, axis,  roomArea,  image));

        // then
        House newHouse = House.createNewHouse(houseId, author_, area, houseImages, rooms);
        assertThat(newHouse.getRooms()).hasSize(1);
    }
}