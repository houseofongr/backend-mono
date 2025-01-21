package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HomeJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@PersistenceAdapterTest
@Import(HomePersistenceAdapter.class)
class HomePersistenceAdapterTest {

    @Autowired
    HomePersistenceAdapter sut;

    @Autowired
    HomeJpaRepository homeJpaRepository;


    @Test
    @Sql("HomePersistenceAdapter.sql")
    @DisplayName("홈 저장 테스트")
    void testSaveHome() {
        // given
        CreateHomeCommand command = new CreateHomeCommand(10L, 20L);

        // when
        CreateHomeResult result = sut.save(command);
        Optional<HomeJpaEntity> find = homeJpaRepository.findById(result.createdHomeId());

        // then
        assertThat(result.createdHomeId()).isNotNull();
        assertThat(find).isNotEmpty();
        assertThat(find.get().getUser().getId()).isEqualTo(10);
        assertThat(find.get().getHouse().getId()).isEqualTo(20);
    }
}