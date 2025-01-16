package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.aoo.admin.application.port.in.QueryHouseListCommand;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomNameDuplicatedException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.common.FixtureRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.hoo.aoo.admin.application.service.CreateHouseService.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({HousePersistenceAdapter.class, HouseMapper.class})
class HousePersistenceAdapterTest {

    @Autowired
    HousePersistenceAdapter sut;

    @Autowired
    HouseJpaRepository houseJpaRepository;

    @Autowired
    EntityManager em;
    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Test
    @DisplayName("House 저장 테스트")
    void testSaveHouse() throws AreaLimitExceededException, AxisLimitExceededException, RoomNameDuplicatedException, HouseRelationshipException {
        // given
        HouseId houseId = new HouseId("cozy house", "leaf", "it's very cozy.");

        Room newRoom = Room.create(houseId, "거실", 0F, 0F, 0F, 100F, 100F);
        House newHouse = House.create(houseId, 5000F, 5000F, List.of(newRoom));
        Map<String, Long> imageFileIdMap = Map.of(BASIC_HOUSE_IMAGE_ID, 2L, HOUSE_BORDER_IMAGE_ID, 3L, newRoom.getId().getName(), 1L);

        // when
        Long savedId = sut.save(newHouse, List.of(newRoom), imageFileIdMap);
        em.flush();
        em.clear();

        Optional<HouseJpaEntity> entity = houseJpaRepository.findById(savedId);

        // then
        assertThat(savedId).isNotNull();
        assertThat(entity).isNotEmpty();
        assertThat(entity.get().getTitle()).isEqualTo("cozy house");
        assertThat(entity.get().getAuthor()).isEqualTo("leaf");
        assertThat(entity.get().getDescription()).isEqualTo("it's very cozy.");
        assertThat(entity.get().getRooms()).hasSize(1);
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("HouseJpaEntity 페이지 조회 테스트")
    void testSearchJpaEntityHouse() {
        // given
        Pageable pageable = PageRequest.of(0,9);

        QueryHouseListCommand allCommand = new QueryHouseListCommand(pageable, null, null);
        QueryHouseListCommand keywordCommand = new QueryHouseListCommand(pageable, SearchType.TITLE, "cozy");
        QueryHouseListCommand keywordCommand2 = new QueryHouseListCommand(pageable, SearchType.AUTHOR, "leAf");
        QueryHouseListCommand keywordCommand3 = new QueryHouseListCommand(pageable, SearchType.DESCRIPTION, "IS");
        QueryHouseListCommand nonKeywordCommand = new QueryHouseListCommand(pageable, SearchType.DESCRIPTION, "no keyword");

        // when
        Page<HouseJpaEntity> entities = sut.search(allCommand);
        Page<HouseJpaEntity> searchEntities = sut.search(keywordCommand);
        Page<HouseJpaEntity> searchEntities2 = sut.search(keywordCommand2);
        Page<HouseJpaEntity> searchEntities3 = sut.search(keywordCommand3);
        Page<HouseJpaEntity> noEntities = sut.search(nonKeywordCommand);

        // then
        assertThat(entities).hasSize(9);
        assertThat(entities).anySatisfy(entity -> {
                assertThat(entity.getId()).isEqualTo(1L);
                assertThat(entity.getTitle()).isEqualTo("cozy house");
                assertThat(entity.getAuthor()).isEqualTo("leaf");
                assertThat(entity.getDescription()).isEqualTo("this is cozy house");
                assertThat(entity.getWidth()).isEqualTo(5000);
                assertThat(entity.getHeight()).isEqualTo(5000);
                assertThat(entity.getBasicImageFileId()).isEqualTo(1L);
                assertThat(entity.getBorderImageFileId()).isEqualTo(2L);
                assertThat(entity.getRooms()).hasSize(2);
        });

        // keyword search
        assertThat(searchEntities).hasSize(1);
        assertThat(searchEntities2).hasSize(1);
        assertThat(searchEntities3).hasSize(1);
        assertThat(noEntities).hasSize(0);
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("HouseJpaEntity 단건 조회 테스트")
    void testFindJpaEntityHouse() {
        // given
        Long houseId = 1L;

        // when
        Optional<HouseJpaEntity> query = sut.findJpaEntity(houseId);

        // then
        assertThat(query).isNotEmpty();
        assertThat(query.get().getId()).isEqualTo(houseId);
        assertThat(query.get().getTitle()).isEqualTo("cozy house");
        assertThat(query.get().getAuthor()).isEqualTo("leaf");
        assertThat(query.get().getDescription()).isEqualTo("this is cozy house");
        assertThat(query.get().getWidth()).isEqualTo(5000f);
        assertThat(query.get().getHeight()).isEqualTo(5000f);
        assertThat(query.get().getBasicImageFileId()).isEqualTo(1L);
        assertThat(query.get().getBorderImageFileId()).isEqualTo(2L);
        assertThat(query.get().getRooms()).hasSize(2)
                .anySatisfy(room -> {
                    assertThat(room.getId()).isEqualTo(1L);
                    assertThat(room.getName()).isEqualTo("거실");
                    assertThat(room.getX()).isEqualTo(0);
                    assertThat(room.getY()).isEqualTo(0);
                    assertThat(room.getZ()).isEqualTo(0);
                    assertThat(room.getWidth()).isEqualTo(5000);
                    assertThat(room.getHeight()).isEqualTo(1000);
                    assertThat(room.getImageFileId()).isEqualTo(5L);
                    assertThat(room.getHouse()).isEqualTo(query.get());
                });
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("하우스 조회 테스트")
    void testFind() throws AreaLimitExceededException, AxisLimitExceededException {
        // given
        Long houseId = 1L;

        // when
        Optional<House> house = sut.find(houseId);

        // then
        assertThat(house).isNotEmpty();
        assertThat(house.get().getId().getTitle()).isEqualTo("cozy house");
        assertThat(house.get().getId().getAuthor()).isEqualTo("leaf");
        assertThat(house.get().getId().getDescription()).isEqualTo("this is cozy house");
        assertThat(house.get().getArea().getWidth()).isEqualTo(5000f);
        assertThat(house.get().getArea().getHeight()).isEqualTo(5000f);
        assertThat(house.get().getRooms()).hasSize(2)
                .anySatisfy(room -> {
                    assertThat(room.getId().getHouseId()).isEqualTo(house.get().getId());
                    assertThat(room.getId().getName()).isEqualTo("거실");
                    assertThat(room.getAxis().getX()).isEqualTo(0);
                    assertThat(room.getAxis().getY()).isEqualTo(0);
                    assertThat(room.getAxis().getZ()).isEqualTo(0);
                    assertThat(room.getArea().getWidth()).isEqualTo(5000);
                    assertThat(room.getArea().getHeight()).isEqualTo(1000);
                });
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("하우스 수정 테스트")
    void testUpdateInfoHouse() throws Exception {
        // given
        House houseWithRoom = FixtureRepository.getHouseWithRoom(new HouseId("not cozy house", "arang", "this is not cozy house"));

        // when
        sut.update(1L, houseWithRoom);
        Optional<HouseJpaEntity> query = sut.findJpaEntity(1L);

        // then
        assertThat(query).isNotEmpty();
        assertThat(query.get().getTitle()).isEqualTo("not cozy house");
        assertThat(query.get().getAuthor()).isEqualTo("arang");
        assertThat(query.get().getDescription()).isEqualTo("this is not cozy house");
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("룸 수정 테스트")
    void testUpdateRoomInfo() throws AxisLimitExceededException, AreaLimitExceededException {
        // given
        Room 욕실 = FixtureRepository.getRoom(new HouseId("", "", ""), "욕실");

        // when
        sut.update(1L, "거실", 욕실);
        Optional<HouseJpaEntity> query = sut.findJpaEntity(1L);

        // then
        assertThat(query.get().getRooms()).anySatisfy(roomJpaEntity ->{
            assertThat(roomJpaEntity.getName()).isEqualTo("욕실");
        });
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("룸 조회 테스트")
    void testFindRoomEntity() {
        // given
        Long id = 1L;
        String name = "거실";

        // when
        Optional<RoomJpaEntity> jpaEntity = sut.findJpaEntity(id, name);
        Optional<RoomJpaEntity> jpaEntity2 = sut.findJpaEntity(id, "not in house name");

        // then
        assertThat(jpaEntity).isNotEmpty();
        assertThat(jpaEntity.get().getName()).isEqualTo("거실");
        assertThat(jpaEntity.get().getX()).isEqualTo(0);
        assertThat(jpaEntity.get().getY()).isEqualTo(0);
        assertThat(jpaEntity.get().getZ()).isEqualTo(0);
        assertThat(jpaEntity.get().getWidth()).isEqualTo(5000);
        assertThat(jpaEntity.get().getHeight()).isEqualTo(1000);

        // not found
        assertThat(jpaEntity2.isEmpty());
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("하우스 삭제 테스트")
    void testDeleteHouse() {
        // given
        Long id = 1L;

        // when
        sut.delete(id);

        // then
        assertThat(houseJpaRepository.findById(id)).isEmpty();
        assertThat(roomJpaRepository.findAllByHouseId(id)).isEmpty();
    }


    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("룸 삭제 테스트")
    void testDeleteRoom() {
        // given
        Long houseId = 1L;
        String roomName = "거실";

        // when
        sut.delete(houseId, roomName);

        // then
        assertThat(roomJpaRepository.findByHouseIdAndName(houseId, roomName)).isEmpty();
    }
}