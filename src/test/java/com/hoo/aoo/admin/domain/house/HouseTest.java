package com.hoo.aoo.admin.domain.house;

import com.hoo.aoo.admin.domain.exception.*;
import com.hoo.aoo.common.FixtureRepository;
import com.hoo.aoo.admin.domain.house.room.Room;
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
    void testCreateHouse() throws AreaLimitExceededException, RoomNameDuplicatedException, HouseRelationshipException, AxisLimitExceededException {
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
    void testRoomIdDuplication() throws HouseRelationshipException, AreaLimitExceededException, RoomNameDuplicatedException, AxisLimitExceededException {
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
                .isInstanceOf(RoomNameDuplicatedException.class)
                .hasMessage("house 'cozy house' already has room named '거실'.");
    }

    @Test
    @DisplayName("방 참조관계 테스트")
    void testRoomRelationship() throws AxisLimitExceededException, AreaLimitExceededException, HouseRelationshipException, RoomNameDuplicatedException {
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
    void testUpdateInfo() throws HouseRelationshipException, AreaLimitExceededException, RoomNameDuplicatedException, AxisLimitExceededException {
        // given
        HouseId houseId = new HouseId(title, author, description);
        List<Room> rooms = List.of(FixtureRepository.getRoom(houseId, "거실"));

        House newHouse = House.create(houseId, width, height, rooms);

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
    void testUpdateRoomInfo() throws HouseRelationshipException, AxisLimitExceededException, AreaLimitExceededException, RoomNameDuplicatedException, RoomNameNotFoundException {
        // given
        House houseWithRoom = FixtureRepository.getHouseWithRoom();
        String originalName = "거실";
        String newName = "욕실";
        String newName2 = "주방";

        // when
        houseWithRoom.updateRoomInfo(originalName, newName);

        // then
        assertThat(houseWithRoom.getRooms()).anySatisfy(
                room -> assertThat(room.getId().getName()).isEqualTo("욕실")
        );

        assertThatThrownBy(() -> houseWithRoom.updateRoomInfo(originalName, originalName))
                .isInstanceOf(RoomNameNotFoundException.class)
                .hasMessage("house 'cozy house' doesn't have room named '거실'.");

        assertThatThrownBy(() -> houseWithRoom.updateRoomInfo(newName, newName2))
                .isInstanceOf(RoomNameDuplicatedException.class)
                .hasMessage("house 'cozy house' already has room named '주방'.");
    }
}