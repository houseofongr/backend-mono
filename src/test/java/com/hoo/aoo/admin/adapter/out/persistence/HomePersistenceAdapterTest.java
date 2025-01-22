package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.aoo.admin.application.port.in.home.QueryHomeResult;
import com.hoo.aoo.admin.application.port.in.home.QueryUserHomesResult;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.domain.home.Home;
import com.hoo.aoo.common.FixtureRepository;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
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
        CreateHomeCommand command = new CreateHomeCommand(10L, 20L);
        Home home = FixtureRepository.getHome();

        // when
        CreateHomeResult result = sut.save(command, home);
        Optional<HomeJpaEntity> find = homeJpaRepository.findById(result.createdHomeId());

        // then
        assertThat(result.createdHomeId()).isNotNull();
        assertThat(find).isNotEmpty();
        assertThat(find.get().getName()).isEqualTo("leaf의 cozy house");
        assertThat(find.get().getUser().getId()).isEqualTo(10);
        assertThat(find.get().getHouse().getId()).isEqualTo(20);

        assertThatThrownBy(() -> sut.save(command, home)).hasMessage(AdminErrorCode.ALREADY_CREATED_HOME.getMessage());
    }

    @Test
    @Sql("HomePersistenceAdapter2.sql")
    @DisplayName("홈 조회 테스트")
    void testFindHome() {
        // given
        Long id = 1L;
        Long notExistId = 123L;

        // when
        Optional<QueryHomeResult> notExistHome = sut.findHome(notExistId);
        Optional<QueryHomeResult> home = sut.findHome(id);

        // then
        assertThat(notExistHome).isEmpty();
        assertThat(home).isNotEmpty();
        assertThat(home.get().homeId()).isEqualTo(1L);
        assertThat(home.get().homeName()).isEqualTo("leaf의 cozy house");
        assertThat(home.get().house().width()).isEqualTo(5000F);
        assertThat(home.get().house().height()).isEqualTo(5000F);
        assertThat(home.get().house().borderImageId()).isEqualTo(2L);
        assertThat(home.get().rooms()).anySatisfy(roomInfo -> {
            assertThat(roomInfo.roomId()).isEqualTo(1L);
            assertThat(roomInfo.imageId()).isEqualTo(5L);
            assertThat(roomInfo.width()).isEqualTo(5000F);
            assertThat(roomInfo.height()).isEqualTo(1000F);
            assertThat(roomInfo.name()).isEqualTo("거실");
            assertThat(roomInfo.x()).isEqualTo(0L);
            assertThat(roomInfo.y()).isEqualTo(0L);
            assertThat(roomInfo.z()).isEqualTo(0L);
        });
    }

    @Test
    @Sql("HomePersistenceAdapter3.sql")
    @DisplayName("사용자의 홈 조회 테스트")
    void testFindUserHomes() {
        // given
        Long id = 10L;

        // when
        QueryUserHomesResult userHomes = sut.findUserHomes(id);

        // then
        assertThat(userHomes.homes()).hasSize(6);
        assertThat(userHomes.homes()).anySatisfy(home -> {
            assertThat(home.id()).isEqualTo(1L);
            assertThat(home.baseHouse().title()).isEqualTo("cozy house");
            assertThat(home.baseHouse().author()).isEqualTo("leaf");
            assertThat(home.baseHouse().description()).isEqualTo("my cozy house");
            assertThat(home.createdDate()).matches("\\d{4}\\. \\d{2}\\. \\d{2}\\.");
            assertThat(home.updatedDate()).matches("\\d{4}\\. \\d{2}\\. \\d{2}\\.");
        });
    }

    @Test
    @Sql("HomePersistenceAdapter3.sql")
    @DisplayName("홈 삭제 테스트")
    void testDeleteHome() {
        // given
        Long id = 1L;
        Long notExistId = 123L;

        // when
        sut.delete(id);

        // then
        assertThat(homeJpaRepository.findById(id)).isEmpty();
        assertThatThrownBy(() -> sut.delete(notExistId)).hasMessage(AdminErrorCode.HOME_NOT_FOUND.getMessage());
    }
}