package com.hoo.aoo.aar.adapter.out.persistence.adapter;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.domain.SnsAccount;
import com.hoo.aoo.aar.domain.SnsAccountF;
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
@Import({SnsAccountPersistenceAdapter.class, UserMapper.class})
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
        SnsAccount snsAccount = SnsAccountF.KAKAO_NOT_REGISTERED.get();

        // when
        SnsAccount entityById = sut.load(snsAccount.getId());
        Optional<SnsAccount> entityBySnsId = sut.loadNullableWithUser(snsAccount.getSnsId());

        // then
        assertThat(entityById).usingRecursiveComparison().isEqualTo(snsAccount);
        assertThat(entityBySnsId).isNotEmpty();
        assertThat(entityBySnsId.get()).usingRecursiveComparison().isEqualTo(snsAccount);
    }

    @Test
    @DisplayName("SNS Account 저장")
    void testSaveSnsAccount() {
        // given
        SnsAccount snsAccount = SnsAccountF.KAKAO_NOT_REGISTERED_WITH_NO_ID.get();

        // when
        SnsAccount entityInDB = sut.save(snsAccount);

        // then
        assertThat(entityInDB).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(snsAccount);
    }
}