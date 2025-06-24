package com.aoo.aar.adapter.out.persistence;

import com.aoo.aar.adapter.out.persistence.mapper.SnsAccountMapper;
import com.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.aoo.aar.application.port.in.user.SearchMyInfoResult;
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
        SearchMyInfoResult result = sut.queryMyInfo(userId);

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

    @Test
    @DisplayName("닉네임 조회")
    void testExistNickname() {
        // given
        String nickname1 = "leaf";
        String nickname2 = "leaf2";

        // when
        boolean result1 = sut.existUserByNickname(nickname1);
        boolean result2 = sut.existUserByNickname(nickname2);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }
}