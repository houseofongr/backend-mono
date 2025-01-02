package com.hoo.aar.adapter.out.persistence.adapter;

import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntityF;
import com.hoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(SnsAccountPersistenceAdapter.class)
class SnsAccountPersistenceAdapterTest {

    @Autowired
    SnsAccountPersistenceAdapter sut;

    @Autowired
    SnsAccountJpaRepository repository;

    @Test
    @Sql("SnsAccountPersistenceAdapterTest.sql")
    @DisplayName("SNS Account 조회")
    void testLoadSnsAccount() {
        // given
        SnsAccountJpaEntity snsAccount = SnsAccountJpaEntityF.KAKAO.get();

        // when
        Optional<SnsAccountJpaEntity> entityInDB = sut.load(snsAccount.getSnsId());

        // then
        assertThat(entityInDB).isNotEmpty();
        assertThat(entityInDB.get()).usingRecursiveComparison().isEqualTo(snsAccount);
    }

    @Test
    @DisplayName("SNS Account 저장")
    void testSaveSnsAccount() {
        // given
        SnsAccountJpaEntity snsAccount = SnsAccountJpaEntityF.KAKAO_NO_ID.get();

        // when
        SnsAccountJpaEntity entityInDB = sut.save(snsAccount);

        // then
        assertThat(entityInDB).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(snsAccount);
    }
}