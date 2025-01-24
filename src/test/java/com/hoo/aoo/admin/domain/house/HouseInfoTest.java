package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.exception.*;
import com.hoo.aoo.common.FixtureRepository;
import com.hoo.aoo.admin.domain.room.Room;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HouseInfoTest {

    String title = "cozy house";
    String author = "leaf";
    String description = "this is cozy house.";
    Float width = 5000F;
    Float height = 5000F;

    @Test
    @DisplayName("하우스 생성 테스트")
    void testCreateHouse() throws Exception {
        // given
        HouseDetail houseDetail = new HouseDetail(title, author, description);
        List<Room> rooms = List.of(FixtureRepository.getRoom("거실"));

        // when
        House newHouse = FixtureRepository.getHouse();

        // then
        assertThat(newHouse.getRooms()).hasSize(2);
        assertThat(newHouse.getHouseDetail().getTitle()).isEqualTo("cozy house");
        assertThat(newHouse.getHouseDetail().getAuthor()).isEqualTo("leaf");
        assertThat(newHouse.getArea().getWidth()).isEqualTo(5000);
        assertThat(newHouse.getArea().getHeight()).isEqualTo(5000);
    }

    @Test
    @DisplayName("하우스 수정 테스트")
    void testUpdateInfo() throws Exception {
        // given
        House newHouse = FixtureRepository.getHouse();

        String title = "not cozy house";
        String author = null;
        String description = "this is not cozy house.";

        // when
        newHouse.updateInfo(title, author, description);

        // then
        assertThat(newHouse.getHouseDetail().getTitle()).isEqualTo(title);
        assertThat(newHouse.getHouseDetail().getAuthor()).isEqualTo("leaf");
        assertThat(newHouse.getHouseDetail().getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("룸 수정 테스트")
    void testUpdateRoomInfo() throws Exception {
        // given
        House houseWithRoom = FixtureRepository.getHouse();
        String originalName = "거실";
        String newName = "욕실";

        // when
        houseWithRoom.updateRoomInfo(originalName, newName);

        // then
        assertThat(houseWithRoom.getRooms()).anySatisfy(
                room -> assertThat(room.getRoomName().getName()).isEqualTo("욕실")
        );

        assertThatThrownBy(() -> houseWithRoom.updateRoomInfo(originalName, originalName))
                .isInstanceOf(RoomNameNotFoundException.class)
                .hasMessage("house 'cozy house' doesn't have room named '거실'.");

    }
}