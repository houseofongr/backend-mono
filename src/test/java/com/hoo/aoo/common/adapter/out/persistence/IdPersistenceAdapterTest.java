package com.hoo.aoo.common.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import(IdPersistenceAdapter.class)
class IdPersistenceAdapterTest {

    @Autowired
    IdPersistenceAdapter sut;

    @Autowired
    HouseJpaRepository houseJpaRepository;

    @Test
    @Sql("IdPersistenceAdapterTest.sql")
    @DisplayName("하우스 ID 생성 테스트")
    void testCreateHouseId() {
        long count = houseJpaRepository.count();

        // when
        Long id = sut.issueHouseId();

        // then
        assertThat(id).isEqualTo(count + 1);
    }

}