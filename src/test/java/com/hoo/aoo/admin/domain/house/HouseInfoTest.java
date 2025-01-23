package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.exception.*;
import com.hoo.aoo.common.FixtureRepository;
import com.hoo.aoo.admin.domain.house.room.Room;
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
        HouseId houseId = new HouseId(title, author, description);
        List<Room> rooms = List.of(FixtureRepository.getRoom("거실"));

        // when
        House newHouse = House.create(houseId, width, height, 1L, 1L, rooms);

        // then
        assertThat(newHouse.getRooms()).hasSize(1);
        assertThat(newHouse.getId().getTitle()).isEqualTo("cozy house");
        assertThat(newHouse.getId().getAuthor()).isEqualTo("leaf");
        assertThat(newHouse.getArea().getWidth()).isEqualTo(5000);
        assertThat(newHouse.getArea().getHeight()).isEqualTo(5000);
    }

    @Test
    @DisplayName("방 ID 중복 테스트")
    void testRoomIdDuplication() throws Exception {
        // given
        HouseId houseId = new HouseId(title, author, description);

        // when
        List<Room> rooms = List.of(
                FixtureRepository.getRoom("거실"),
                FixtureRepository.getRoom("주방")
        );

        // then
        assertThat(House.create(houseId, width, height, 1L, 1L, rooms)).isNotNull();
    }

    @Test
    @DisplayName("하우스 수정 테스트")
    void testUpdateInfo() throws Exception {
        // given
        HouseId houseId = new HouseId(title, author, description);
        List<Room> rooms = List.of(FixtureRepository.getRoom("거실"));

        House newHouse = House.create(houseId, width, height, 1L,1L,rooms);

        String title = "not cozy house";
        String author = null;
        String description = "this is not cozy house.";

        // when
        newHouse.updateInfo(title, author, description);

        // then
        assertThat(newHouse.getId().getTitle()).isEqualTo(title);
        assertThat(newHouse.getId().getAuthor()).isEqualTo("leaf");
        assertThat(newHouse.getId().getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("룸 수정 테스트")
    void testUpdateRoomInfo() throws Exception {
        // given
        House houseWithRoom = FixtureRepository.getHouseWithRoom();
        String originalName = "거실";
        String newName = "욕실";

        // when
        houseWithRoom.updateRoomInfo(originalName, newName);

        // then
        assertThat(houseWithRoom.getRooms()).anySatisfy(
                room -> assertThat(room.getId().getName()).isEqualTo("욕실")
        );

        assertThatThrownBy(() -> houseWithRoom.updateRoomInfo(originalName, originalName))
                .isInstanceOf(RoomNameNotFoundException.class)
                .hasMessage("house 'cozy house' doesn't have room named '거실'.");

    }
}