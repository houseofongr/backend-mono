package com.aoo.aar.adapter.out.persistence;

import com.aoo.aar.adapter.out.persistence.mapper.SnsAccountMapper;
import com.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.aoo.aar.application.port.in.user.QueryMyInfoResult;
import com.aoo.common.adapter.out.persistence.PersistenceAdapterTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@PersistenceAdapterTest
@Import({UserPersistenceAdapter.class, UserMapper.class, SnsAccountMapper.class})
@Sql("UserPersistenceAdapterTest.sql")
class UserPersistenceAdapterTest {

    @Autowired
    UserPersistenceAdapter sut;

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