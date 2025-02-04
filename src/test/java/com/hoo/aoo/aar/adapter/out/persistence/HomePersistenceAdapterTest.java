package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.HomeMapper;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;
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
        QueryUserHomesResult result = sut.queryUserHome(userId);

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
}