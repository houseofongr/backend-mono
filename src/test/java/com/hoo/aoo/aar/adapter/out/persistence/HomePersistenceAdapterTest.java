package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.aoo.aar.application.port.in.home.QueryHomeRoomsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryRoomItemsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.admin.domain.item.ItemType;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({HomePersistenceAdapter.class, HomeMapper.class})
class HomePersistenceAdapterTest {

    @Autowired
    HomePersistenceAdapter sut;

    @Test
    @Sql("HomePersistenceAdapterTest.sql")
    @DisplayName("사용자 홈 조회 테스트")
    void testQueryUserHomes() {
        // given
        Long userId = 10L;

        // when
        QueryUserHomesResult result = sut.queryUserHomes(userId);

        // then
        assertThat(result.homes()).hasSize(2)
                .anySatisfy(homeInfo -> {
                    assertThat(homeInfo.id()).isEqualTo(1L);
                    assertThat(homeInfo.name()).isEqualTo("leaf의 cozy house");
                })
                .anySatisfy(homeInfo -> {
                    assertThat(homeInfo.id()).isEqualTo(2L);
                    assertThat(homeInfo.name()).isEqualTo("leaf의 simple house");
                });
    }

    @Test
    @Sql("HomePersistenceAdapterTest.sql")
    @DisplayName("사용자 홈 소유여부 확인 테스트")
    void testCheckUserOwnHome() {
        // given
        Long ownedUserId = 10L;
        Long notOwnedUserId = 1234L;
        Long homeId = 1L;
        Long notOwnedHomeId = 5678L;

        // when
        assertThat(sut.checkHome(ownedUserId, homeId)).isTrue();
        assertThat(sut.checkHome(notOwnedUserId, homeId)).isFalse();
        assertThat(sut.checkHome(ownedUserId, notOwnedHomeId)).isFalse();
        assertThat(sut.checkHome(notOwnedUserId, notOwnedHomeId)).isFalse();
    }

    @Test
    @Sql("HomePersistenceAdapterTest.sql")
    @DisplayName("홈 룸 소유여부 확인 테스트")
    void testCheckHomeOwnRoom() {
        // given
        Long ownedHomeId = 1L;
        Long roomId = 2L;
        Long notOwnedRoomId = 5678L;

        // when
        assertThat(sut.checkRoom(ownedHomeId, roomId)).isTrue();
        assertThat(sut.checkRoom(ownedHomeId, notOwnedRoomId)).isFalse();
    }

    @Test
    @Sql("HomePersistenceAdapterTest.sql")
    @DisplayName("홈 룸 조회 테스트")
    void testQueryHomeRooms() {
        // given
        Long homeId = 1L;

        // when
        QueryHomeRoomsResult result = sut.queryHomeRooms(homeId);

        // then
        assertThat(result.homeName()).isEqualTo("leaf의 cozy house");
        assertThat(result.house().borderImageId()).isEqualTo(2L);
        assertThat(result.house().height()).isEqualTo(5000);
        assertThat(result.house().width()).isEqualTo(5000);
        assertThat(result.rooms()).hasSize(2)
                .anySatisfy(roomInfo -> {
                    assertThat(roomInfo.roomId()).isEqualTo(1L);
                    assertThat(roomInfo.name()).isEqualTo("거실");
                    assertThat(roomInfo.width()).isEqualTo(5000);
                    assertThat(roomInfo.height()).isEqualTo(1000);
                    assertThat(roomInfo.x()).isEqualTo(0);
                    assertThat(roomInfo.y()).isEqualTo(0);
                    assertThat(roomInfo.z()).isEqualTo(0);
                    assertThat(roomInfo.imageId()).isEqualTo(5);
                })
                .anySatisfy(roomInfo -> {
                    assertThat(roomInfo.roomId()).isEqualTo(2L);
                    assertThat(roomInfo.name()).isEqualTo("주방");
                    assertThat(roomInfo.width()).isEqualTo(5000);
                    assertThat(roomInfo.height()).isEqualTo(1000);
                    assertThat(roomInfo.x()).isEqualTo(0);
                    assertThat(roomInfo.y()).isEqualTo(1000);
                    assertThat(roomInfo.z()).isEqualTo(0);
                    assertThat(roomInfo.imageId()).isEqualTo(6);
                });
    }

    @Test
    @Sql("HomePersistenceAdapterTest.sql")
    @DisplayName("룸 아이템 조회 테스트")
    void testQueryRoomItems() {
        // given
        Long roomId = 1L;

        // when
        QueryRoomItemsResult queryRoomItemsResult = sut.queryRoomItems(roomId);

        // then
        assertThat(queryRoomItemsResult.room().name()).isEqualTo("거실");
        assertThat(queryRoomItemsResult.room().width()).isEqualTo(5000);
        assertThat(queryRoomItemsResult.room().height()).isEqualTo(1000);
        assertThat(queryRoomItemsResult.room().imageId()).isEqualTo(5);

        assertThat(queryRoomItemsResult.items()).hasSize(3)
                .anySatisfy(itemData -> {
                    assertThat(itemData.id()).isEqualTo(1);
                    assertThat(itemData.name()).isEqualTo("설이");
                    assertThat(itemData.itemType()).isEqualTo(ItemType.RECTANGLE);
                    assertThat(itemData.rectangleData().x()).isEqualTo(100);
                    assertThat(itemData.rectangleData().y()).isEqualTo(100);
                    assertThat(itemData.rectangleData().width()).isEqualTo(10);
                    assertThat(itemData.rectangleData().height()).isEqualTo(10);
                    assertThat(itemData.rectangleData().rotation()).isEqualTo(5);
                    assertThat(itemData.circleData()).isNull();
                    assertThat(itemData.ellipseData()).isNull();
                })
                .noneMatch(itemData -> itemData != null && itemData.id().equals(2L))
                .noneMatch(itemData -> itemData != null && itemData.id().equals(3L));
    }
}