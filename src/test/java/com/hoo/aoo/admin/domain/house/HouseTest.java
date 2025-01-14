package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.application.port.in.CreateHouseResult;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomDuplicatedException;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.admin.domain.room.RoomId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HouseTest {

    Long persistenceId = 1L;
    String title = "cozy house";
    String author = "leaf";
    String description = "this is cozy house.";
    Integer width = 5000;
    Integer height = 5000;
    Long basicImageId = 1L;
    Long borderImageId = 2L;

    @Test
    @DisplayName("집 생성 테스트")
    void testCreateHouse() throws AreaLimitExceededException, RoomDuplicatedException, HouseRelationshipException {
        // given
        HouseId houseId = new HouseId(title, author, description);
        List<RoomId> rooms = List.of(new RoomId(houseId, "거실"));

        // when
        House newHouse = House.create(houseId, width, height, rooms);

        // then
        assertThat(newHouse.getRooms()).hasSize(1);
        assertThat(newHouse.getId().getTitle()).isEqualTo("cozy house");
        assertThat(newHouse.getId().getAuthor()).isEqualTo("leaf");
        assertThat(newHouse.getArea().getWidth()).isEqualTo(5000);
        assertThat(newHouse.getArea().getHeight()).isEqualTo(5000);
    }

    @Test
    @DisplayName("방 ID 중복 테스트")
    void testRoomIdDuplication() throws HouseRelationshipException, AreaLimitExceededException, RoomDuplicatedException {
        // given
        HouseId houseId = new HouseId(title, author, description);

        // when
        List<RoomId> rooms = List.of(
                new RoomId(houseId, "거실"),
                new RoomId(houseId, "주방"));

        List<RoomId> roomsDuplicated = List.of(
                new RoomId(houseId, "거실"),
                new RoomId(houseId, "거실"));

        // then
        assertThat(House.create(houseId, width, height, rooms)).isNotNull();

        assertThatThrownBy(() -> House.create(houseId, width, height, roomsDuplicated))
                .isInstanceOf(RoomDuplicatedException.class)
                .hasMessage("Room name 거실 is duplicated.");
    }

    @Test
    @DisplayName("방 참조관계 테스트")
    void testRoomRelationship() {
        // given
        HouseId houseId = new HouseId(title, author, description);

        // when
        List<RoomId> rooms = List.of(
                new RoomId(null, "거실"));

        List<RoomId> roomsToAnotherHouse = List.of(
                new RoomId(new HouseId("test", "tester","test house id"), "거실"));


        // then
        assertThatThrownBy(() -> House.create(houseId, width, height, rooms))
                .isInstanceOf(HouseRelationshipException.class)
                .hasMessage("Room name 거실 doesn't related to " + houseId);

        assertThatThrownBy(() -> House.create(houseId, width, height, roomsToAnotherHouse))
                .isInstanceOf(HouseRelationshipException.class)
                .hasMessage("Room name 거실 doesn't related to " + houseId);
    }

    @Test
    @DisplayName("집 조회 테스트")
    void testLoadRoom() {
        // given

        // when

        // then
    }
}