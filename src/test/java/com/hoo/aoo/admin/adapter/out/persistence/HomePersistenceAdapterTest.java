package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.repository.HomeJpaRepository;
import com.hoo.aoo.admin.application.port.in.user.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.user.CreateHomeResult;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@PersistenceAdapterTest
@Import(HomePersistenceAdapter.class)
class HomePersistenceAdapterTest {

    @Autowired
    HomePersistenceAdapter sut;

    @Autowired
    HomeJpaRepository homeJpaRepository;

    @BeforeEach
    void init() {
        sut = new HomePersistenceAdapter(homeJpaRepository);
    }

    @Test
    @Sql("HomePersistenceAdapter.sql")
    @DisplayName("홈 저장 테스트")
    void testSaveHome() {
        // given
        CreateHomeCommand command = new CreateHomeCommand(10L, 20L);

        // when
        CreateHomeResult result = sut.save(command);

        // then
        assertThat(result.createdHomeId()).isNotNull();
    }
}