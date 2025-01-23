package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListCommand;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListResult;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseResult;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.Detail;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.common.FixtureRepository;
import com.hoo.aoo.common.adapter.in.web.DateTimeFormatters;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
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

    @Autowired
    RoomJpaRepository roomJpaRepository;

    @Test
    @DisplayName("House 저장 테스트")
    void testSaveHouse() throws Exception {
        // given
        Detail detail = new Detail("cozy house", "leaf", "it's very cozy.");

        House newHouse = FixtureRepository.getHouse(detail);

        // when
        Long savedId = sut.save(newHouse);
        em.flush();
        em.clear();

        Optional<HouseJpaEntity> entity = houseJpaRepository.findById(savedId);

        // then
        assertThat(savedId).isNotNull();
        assertThat(entity).isNotEmpty();
        assertThat(entity.get().getTitle()).isEqualTo("cozy house");
        assertThat(entity.get().getAuthor()).isEqualTo("leaf");
        assertThat(entity.get().getDescription()).isEqualTo("it's very cozy.");
        assertThat(entity.get().getRooms()).hasSize(2);
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("HouseJpaEntity 페이지 조회 테스트")
    void testSearchJpaEntityHouse() {
        // given
        Pageable pageable = PageRequest.of(0, 9);

        QueryHouseListCommand allCommand = new QueryHouseListCommand(pageable, null, null);
        QueryHouseListCommand keywordCommand = new QueryHouseListCommand(pageable, SearchType.TITLE, "cozy");
        QueryHouseListCommand keywordCommand2 = new QueryHouseListCommand(pageable, SearchType.AUTHOR, "leAf");
        QueryHouseListCommand keywordCommand3 = new QueryHouseListCommand(pageable, SearchType.DESCRIPTION, "IS");
        QueryHouseListCommand nonKeywordCommand = new QueryHouseListCommand(pageable, SearchType.DESCRIPTION, "no keyword");

        // when
        QueryHouseListResult result = sut.search(allCommand);
        QueryHouseListResult searchResult = sut.search(keywordCommand);
        QueryHouseListResult searchResult2 = sut.search(keywordCommand2);
        QueryHouseListResult searchResult3 = sut.search(keywordCommand3);
        QueryHouseListResult noResult = sut.search(nonKeywordCommand);

        // then
        assertThat(result.houses()).hasSize(9);
        assertThat(result.houses()).anySatisfy(entity -> {
            assertThat(entity.id()).isEqualTo(1L);
            assertThat(entity.title()).isEqualTo("cozy house");
            assertThat(entity.author()).isEqualTo("leaf");
            assertThat(entity.description()).isEqualTo("this is cozy house");
            assertThat(entity.createdDate()).isEqualTo(DateTimeFormatters.ENGLISH_DATE.getFormatter().format(ZonedDateTime.now()));
        });

        // keyword search
        assertThat(searchResult.houses()).hasSize(1);
        assertThat(searchResult2.houses()).hasSize(1);
        assertThat(searchResult3.houses()).hasSize(1);
        assertThat(noResult.houses()).hasSize(0);
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("HouseJpaEntity 단건 조회 테스트")
    void testFindHouseJpaEntityQueryHouse() {
        // given
        Long houseId = 1L;

        // when
        Optional<QueryHouseResult> query = sut.findResult(houseId);

        // then
        assertThat(query).isNotEmpty();
        assertThat(query.get().house().houseId()).isEqualTo(houseId);
        assertThat(query.get().house().title()).isEqualTo("cozy house");
        assertThat(query.get().house().author()).isEqualTo("leaf");
        assertThat(query.get().house().description()).isEqualTo("this is cozy house");
        assertThat(query.get().house().width()).isEqualTo(5000f);
        assertThat(query.get().house().height()).isEqualTo(5000f);
        assertThat(query.get().house().borderImageId()).isEqualTo(2L);
        assertThat(query.get().rooms()).hasSize(2)
                .anySatisfy(room -> {
                    assertThat(room.roomId()).isEqualTo(1L);
                    assertThat(room.name()).isEqualTo("거실");
                    assertThat(room.x()).isEqualTo(0);
                    assertThat(room.y()).isEqualTo(0);
                    assertThat(room.z()).isEqualTo(0);
                    assertThat(room.width()).isEqualTo(5000);
                    assertThat(room.height()).isEqualTo(1000);
                    assertThat(room.imageId()).isEqualTo(5L);
                });
    }

    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("하우스 조회 테스트")
    void testLoad() throws AreaLimitExceededException, AxisLimitExceededException {
        // given
        Long houseId = 1L;

        // when
        Optional<House> house = sut.load(houseId);

        // then
        assertThat(house).isNotEmpty();
        assertThat(house.get().getDetail().getTitle()).isEqualTo("cozy house");
        assertThat(house.get().getDetail().getAuthor()).isEqualTo("leaf");
        assertThat(house.get().getDetail().getDescription()).isEqualTo("this is cozy house");
        assertThat(house.get().getArea().getWidth()).isEqualTo(5000f);
        assertThat(house.get().getArea().getHeight()).isEqualTo(5000f);
        assertThat(house.get().getRooms()).hasSize(2)
                .anySatisfy(room -> {
                    assertThat(room.getDetail().getName()).isEqualTo("거실");
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
        House houseWithRoom = FixtureRepository.getHouse(new Detail("not cozy house", "arang", "this is not cozy house"));

        // when
        sut.update(1L, houseWithRoom);
        Optional<HouseJpaEntity> query = houseJpaRepository.findById(1L);

        // then
        assertThat(query).isNotEmpty();
        assertThat(query.get().getTitle()).isEqualTo("not cozy house");
        assertThat(query.get().getAuthor()).isEqualTo("arang");
        assertThat(query.get().getDescription()).isEqualTo("this is not cozy house");
    }


    @Test
    @Sql("HousePersistenceAdapterTest.sql")
    @DisplayName("하우스 삭제 테스트")
    void testDeleteHouseRoomHouse() {
        // given
        Long id = 1L;

        // when
        sut.deleteHouse(id);

        // then
        assertThat(houseJpaRepository.findById(id)).isEmpty();
        assertThat(roomJpaRepository.findAllByHouseId(id)).isEmpty();
    }

}