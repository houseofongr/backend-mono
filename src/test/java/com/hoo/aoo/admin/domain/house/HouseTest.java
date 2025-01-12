package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.room.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Long borderImageId = 2L;
        List<Room> rooms = List.of(Room.createNewRoom(title, null, 1, 1, 1, 1, 1, null));

        // when
        House newHouse = House.createNewHouse(title, author, width, height, basicImageId, borderImageId, rooms);

        // then
        assertThat(newHouse.getRooms()).hasSize(1);
        assertThat(newHouse.getId().getTitle()).isEqualTo("cozy house");
        assertThat(newHouse.getAuthor().getName()).isEqualTo("leaf");
        assertThat(newHouse.getArea().getWidth()).isEqualTo(5000);
        assertThat(newHouse.getArea().getHeight()).isEqualTo(5000);
        assertThat(newHouse.getImages().getBasicImageId()).isEqualTo(1L);
        assertThat(newHouse.getImages().getBorderImageId()).isEqualTo(2L);
    }
}