package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.FixtureRepository;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomDuplicatedException;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.admin.domain.house.room.RoomId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HouseTest {

    String title = "cozy house";
    String author = "leaf";
    String description = "this is cozy house.";
    Float width = 5000F;
    Float height = 5000F;

    @Test
    @DisplayName("하우스 생성 테스트")
    void testCreateHouse() throws AreaLimitExceededException, RoomDuplicatedException, HouseRelationshipException, AxisLimitExceededException {
        // given
        HouseId houseId = new HouseId(title, author, description);
        List<Room> rooms = List.of(FixtureRepository.getRoom(houseId, "거실"));

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
    void testRoomIdDuplication() throws HouseRelationshipException, AreaLimitExceededException, RoomDuplicatedException, AxisLimitExceededException {
        // given
        HouseId houseId = new HouseId(title, author, description);

        // when
        List<Room> rooms = List.of(
                FixtureRepository.getRoom(houseId, "거실"),
                FixtureRepository.getRoom(houseId, "주방")
        );

        List<Room> roomsDuplicated = List.of(
                FixtureRepository.getRoom(houseId, "거실"),
                FixtureRepository.getRoom(houseId, "거실")
        );
                // then
        assertThat(House.create(houseId, width, height, rooms)).isNotNull();

        assertThatThrownBy(() -> House.create(houseId, width, height, roomsDuplicated))
                .isInstanceOf(RoomDuplicatedException.class)
                .hasMessage("Room name 거실 is duplicated.");
    }

    @Test
    @DisplayName("방 참조관계 테스트")
    void testRoomRelationship() throws AxisLimitExceededException, AreaLimitExceededException, HouseRelationshipException, RoomDuplicatedException {
        // given
        HouseId houseId = new HouseId(title, author, description);

        // when
        List<Room> rooms = List.of(
                FixtureRepository.getRoom(houseId, "거실")
        );

        List<Room> noHouseRoom = List.of(
                FixtureRepository.getRoom(null, "거실")
        );


        List<Room> roomsToAnotherHouse = List.of(
                FixtureRepository.getRoom(new HouseId("test", "tester","test house id"), "거실")
        );


        // then
        House.create(houseId,width,height,rooms);

        assertThatThrownBy(() -> House.create(houseId, width, height, noHouseRoom))
                .isInstanceOf(HouseRelationshipException.class)
                .hasMessage("Room name 거실 doesn't related to " + houseId);

        assertThatThrownBy(() -> House.create(houseId, width, height, roomsToAnotherHouse))
                .isInstanceOf(HouseRelationshipException.class)
                .hasMessage("Room name 거실 doesn't related to " + houseId);
    }

    @Test
    @DisplayName("하우스 수정 테스트")
    void testUpdate() throws HouseRelationshipException, AreaLimitExceededException, RoomDuplicatedException, AxisLimitExceededException {
        // given
        HouseId houseId = new HouseId(title, author, description);
        List<Room> rooms = List.of(FixtureRepository.getRoom(houseId, "거실"));

        House newHouse = House.create(houseId, width, height, rooms);

        String title = "not cozy house";
        String author = null;
        String description = "this is not cozy house.";
        Float width = null;
        Float height = 4500F;

        // when
        newHouse.update(title, author, description, width, height);

        // then
        assertThat(newHouse.getId().getTitle()).isEqualTo(title);
        assertThat(newHouse.getId().getAuthor()).isEqualTo("leaf");
        assertThat(newHouse.getId().getDescription()).isEqualTo(description);
        assertThat(newHouse.getArea().getWidth()).isEqualTo(5000F);
        assertThat(newHouse.getArea().getHeight()).isEqualTo(4500F);
    }
}