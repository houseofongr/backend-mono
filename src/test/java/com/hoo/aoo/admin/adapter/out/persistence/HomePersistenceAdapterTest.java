package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseDetail;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.aoo.common.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@PersistenceAdapterTest
@Import({HomePersistenceAdapter.class, HomeMapper.class})
class HomePersistenceAdapterTest {

    @Autowired
    HomePersistenceAdapter sut;

    @Autowired
    HomeJpaRepository homeJpaRepository;


    @Test
    @Sql("HomePersistenceAdapter.sql")
    @DisplayName("홈 저장 테스트")
    void testSaveHome() throws Exception {
        // given
        Home home = Home.create(1L,
                House.create(20L,"cozy house", "leaf", "this is cozy house",5000f,5000f,null,null,null),
                User.load(10L, "남상엽","leaf","test@example.com",true,true,null,null,null));

        // when
        Long result = sut.save(home);
        Optional<HomeJpaEntity> find = homeJpaRepository.findById(result);

        // then
        assertThat(result).isNotNull();
        assertThat(find).isNotEmpty();
        assertThat(find.get().getName()).isEqualTo("leaf의 cozy house");
        assertThat(find.get().getUser().getId()).isEqualTo(10);
        assertThat(find.get().getHouse().getId()).isEqualTo(20);

        assertThatThrownBy(() -> sut.save(home)).hasMessage(AdminErrorCode.ALREADY_CREATED_HOME.getMessage());
    }

    @Test
    @Sql("HomePersistenceAdapter2.sql")
    @DisplayName("하우스의 홈 존재여부 확인 테스트")
    void testExistByHouseId() {
        Long existId = 20L;
        Long notExistId = 21L;

        assertThat(sut.existByHouseId(existId)).isTrue();
        assertThat(sut.existByHouseId(notExistId)).isFalse();
    }

    @Test
    @Sql("HomePersistenceAdapter2.sql")
    @DisplayName("홈 조회 테스트")
    void testFindHome() {
        // given
        Long id = 1L;
        Long notExistId = 123L;

        // when
        Optional<Home> notExistHome = sut.loadHome(notExistId);
        Optional<Home> home = sut.loadHome(id);

        // then
        assertThat(notExistHome).isEmpty();
        assertThat(home).isNotEmpty();
        assertThat(home.get().getHomeId().getId()).isEqualTo(1L);
        assertThat(home.get().getHomeDetail().getName()).isEqualTo("leaf의 cozy house");
        assertThat(home.get().getHouseId().getId()).isEqualTo(20L);
        assertThat(home.get().getUserId().getId()).isEqualTo(10L);
    }

    @Test
    @Sql("HomePersistenceAdapter2.sql")
    @DisplayName("홈 수정 테스트")
    void testUpdateHomeName() {
        // given
        Home home = Home.load(1L, 20L, 10L, "수정된 이름", null, null);

        // when
        sut.updateHome(home);
        HomeJpaEntity homeJpaEntity = homeJpaRepository.findById(home.getHomeId().getId()).orElseThrow();

        // then
        assertThat(homeJpaEntity.getName()).isEqualTo("수정된 이름");

    }

    @Test
    @Sql("HomePersistenceAdapter3.sql")
    @DisplayName("홈 삭제 테스트")
    void testDeleteHomeHome() {
        // given
        Long id = 1L;
        Long notExistId = 123L;

        // when
        sut.deleteHome(id);

        // then
        assertThat(homeJpaRepository.findById(id)).isEmpty();
        assertThatThrownBy(() -> sut.deleteHome(notExistId)).hasMessage(AdminErrorCode.HOME_NOT_FOUND.getMessage());
    }
}