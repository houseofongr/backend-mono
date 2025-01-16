package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.SnsAccountF;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
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

    @Autowired
    UserMapper userMapper;

    @Test
    @Sql("SnsAccountPersistenceAdapterTest.sql")
    @DisplayName("SNS Account 조회")
    void testFindSnsAccount() throws InvalidPhoneNumberException {
        // given
        SnsAccount snsAccount = SnsAccountF.REGISTERED_KAKAO.get();

        // when
        Optional<SnsAccount> entityById = sut.find(1L);
        Optional<SnsAccount> entityBySnsId = sut.find(snsAccount.getSnsAccountId().getSnsDomain(), snsAccount.getSnsAccountId().getSnsId());

        // then
        assertThat(entityById).isNotEmpty();
        assertThat(entityBySnsId).isNotEmpty();
        assertThat(entityById.get()).usingRecursiveComparison().ignoringFields("dateInfo").isEqualTo(snsAccount);
        assertThat(entityBySnsId.get()).usingRecursiveComparison().ignoringFields("dateInfo").isEqualTo(snsAccount);
    }

    @Test
    @DisplayName("SNS Account 저장")
    void testSaveSnsAccount() throws InvalidPhoneNumberException {
        // given
        SnsAccount snsAccount = SnsAccountF.NOT_REGISTERED_KAKAO.get();

        // when
        sut.save(snsAccount);

        // then
        assertThat(repository.findWithUserEntity(snsAccount.getSnsAccountId().getSnsDomain(), snsAccount.getSnsAccountId().getSnsId()))
                .get().usingRecursiveComparison()
                .ignoringFields("id", "createdTime", "updatedTime")
                .isEqualTo(userMapper.mapToNewJpaEntity(snsAccount));
    }
}