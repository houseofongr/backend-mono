package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.SnsAccountMapper;
import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.assertj.core.data.Offset;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({UserPersistenceAdapter.class, UserMapper.class, SnsAccountMapper.class})
class UserPersistenceAdapterTest {

    @Autowired
    UserPersistenceAdapter sut;

    @Autowired
    UserJpaRepository repository;

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    @DisplayName("사용자 저장")
    void testSaveUser() {
        // given
        User user = MockEntityFactoryService.getUser();

        // when
        Long savedId = sut.save(user);
        Optional<UserJpaEntity> optional = repository.findById(savedId);

        // then
        assertThat(optional).isNotEmpty();
        assertThat(optional.get().getNickname()).isEqualTo(user.getUserInfo().getNickname());
        assertThat(optional.get().getRealName()).isEqualTo(user.getUserInfo().getRealName());
        assertThat(optional.get().getPhoneNumber()).isNull();
        assertThat(optional.get().getPersonalInformationAgreement()).isEqualTo(user.getAgreement().getPersonalInformationAgreement());
        assertThat(optional.get().getTermsOfUseAgreement()).isEqualTo(user.getAgreement().getTermsOfUseAgreement());
        assertThat(optional.get().getCreatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(1L, ChronoUnit.SECONDS));
        assertThat(optional.get().getUpdatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(1L, ChronoUnit.SECONDS));
    }
}