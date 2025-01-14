package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.application.port.in.ReadHouseListCommand;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomDuplicatedException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.room.Room;
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
import java.util.Optional;

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

    @Test
    @DisplayName("House 저장 테스트")
    void testSaveHouse() throws AreaLimitExceededException, AxisLimitExceededException, RoomDuplicatedException, HouseRelationshipException {
        // given
        HouseId houseId = new HouseId("cozy house", "leaf", "it's very cozy.");

        Room newRoom = Room.create(houseId, "거실", 0, 0, 0, 100, 100, 1L);
        House newHouse = House.create(houseId, 5000, 5000, List.of(newRoom.getId()));

        // when
        Long savedId = sut.save(newHouse, List.of(newRoom), 2L, 3L);
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
    @DisplayName("HouseJpaEntity 조회 테스트")
    void testQueryHouse() {
        // given
        Pageable pageable = PageRequest.of(0,9);

        ReadHouseListCommand allCommand = new ReadHouseListCommand(pageable, null, null);
        ReadHouseListCommand keywordCommand = new ReadHouseListCommand(pageable, SearchType.TITLE, "cozy");
        ReadHouseListCommand keywordCommand2 = new ReadHouseListCommand(pageable, SearchType.AUTHOR, "leAf");
        ReadHouseListCommand keywordCommand3 = new ReadHouseListCommand(pageable, SearchType.DESCRIPTION, "MY");
        ReadHouseListCommand nonKeywordCommand = new ReadHouseListCommand(pageable, SearchType.DESCRIPTION, "no keyword");

        // when
        Page<HouseJpaEntity> entities = sut.query(allCommand);
        Page<HouseJpaEntity> searchEntities = sut.query(keywordCommand);
        Page<HouseJpaEntity> searchEntities2 = sut.query(keywordCommand2);
        Page<HouseJpaEntity> searchEntities3 = sut.query(keywordCommand3);
        Page<HouseJpaEntity> noEntities = sut.query(nonKeywordCommand);

        // then
        assertThat(entities).hasSize(9);
        assertThat(entities).allSatisfy(entity ->
                assertThat(entity.getRooms()).isEmpty());
        assertThat(entities).anySatisfy(entity -> {
                assertThat(entity.getId()).isEqualTo(1L);
                assertThat(entity.getTitle()).isEqualTo("cozy house");
                assertThat(entity.getAuthor()).isEqualTo("leaf");
                assertThat(entity.getDescription()).isEqualTo("my cozy house");
                assertThat(entity.getWidth()).isEqualTo(5000);
                assertThat(entity.getHeight()).isEqualTo(5000);
                assertThat(entity.getBasicImageFileId()).isEqualTo(1L);
                assertThat(entity.getBorderImageFileId()).isEqualTo(2L);
                assertThat(entity.getRooms()).isEmpty();
        });

        // keyword search
        assertThat(searchEntities).hasSize(1);
        assertThat(searchEntities2).hasSize(1);
        assertThat(searchEntities3).hasSize(1);
        assertThat(noEntities).hasSize(0);
    }
}