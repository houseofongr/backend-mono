package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdminUserPersistenceAdapterTest {

    AdminUserPersistenceAdapter sut;

    @BeforeEach
    void init() {
        sut = new AdminUserPersistenceAdapter();
    }

    @Test
    @DisplayName("사용자 검색 기능 테스트")
    void testSearchUser() {
        // given
        QueryUserInfoCommand command = new QueryUserInfoCommand();

        // when
        QueryUserInfoResult result = sut.search(command);

        // then
//        assertThat(result.users().getContent()).anySatisfy(userInfo -> {
//
//                }
//        );
    }
}