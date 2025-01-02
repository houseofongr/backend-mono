package com.hoo.aar.adapter.out.persistence.adapter;

import com.hoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aar.common.enums.ErrorCode;
import com.hoo.aar.common.exception.AarException;
import com.hoo.aar.domain.User;
import com.hoo.aar.domain.UserF;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({UserPersistenceAdapter.class, UserMapper.class})
class UserPersistenceAdapterTest {

    @Autowired
    UserPersistenceAdapter sut;

    @Autowired
    UserJpaRepository repository;

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    @DisplayName("닉네임 충돌여부 조회")
    void testLoadUser() {
        assertThatThrownBy(() -> sut.assertNotExistNickname("leaf"))
                .isInstanceOf(AarException.class)
                .hasMessage(ErrorCode.NICK_NAME_CONFLICT.getMessage());
    }

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    @DisplayName("사용자 저장")
    void testSaveUser() {
        // given
        User user = UserF.REGISTERED_WITH_NO_ID.get();

        // when
        User savedUser = sut.save(user);

        // then
        assertThat(savedUser).usingRecursiveComparison()
                .ignoringFields("id", "snsAccounts")
                .isEqualTo(user);
    }
}