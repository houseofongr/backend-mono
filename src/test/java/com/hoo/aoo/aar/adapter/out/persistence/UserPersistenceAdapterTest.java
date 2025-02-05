package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.mapper.SnsAccountMapper;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({UserPersistenceAdapter.class, UserMapper.class, SnsAccountMapper.class})
@Sql("UserPersistenceAdapterTest.sql")
class UserPersistenceAdapterTest {

    @Autowired
    UserPersistenceAdapter sut;

    @Autowired
    UserJpaRepository repository;

    @Test
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

    @Test
    @DisplayName("본인정보 조회")
    void testQueryMyInfo() {
        // given
        Long userId = 10L;

        // when
        QueryMyInfoResult result = sut.queryMyInfo(userId);

        // then
        assertThat(result.nickname()).isEqualTo("leaf");
        assertThat(result.email()).isEqualTo("test@example.com");
        assertThat(result.registeredDate()).matches("^(January|February|March|April|May|June|July|August|September|October|November|December)\\.\\d{2}\\.\\s\\d{4}$");
        assertThat(result.termsOfUseAgreement()).isTrue();
        assertThat(result.personalInformationAgreement()).isTrue();
        assertThat(result.myHomeCount()).isEqualTo(2);
        assertThat(result.mySoundSourceCount()).isEqualTo(3);
        assertThat(result.snsAccountInfos()).hasSize(1)
                .anySatisfy(soundSource -> {
                    assertThat(soundSource.domain()).isEqualTo("KAKAO");
                    assertThat(soundSource.email()).isEqualTo("test@example.com");
                });
    }
}