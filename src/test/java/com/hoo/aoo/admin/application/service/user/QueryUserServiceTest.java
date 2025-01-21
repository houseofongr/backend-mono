package com.hoo.aoo.admin.application.service.user;

import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.out.user.SearchUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class QueryUserServiceTest {

    QueryUserService sut;

    SearchUserPort searchUserPort;

    @BeforeEach
    void init() {
        searchUserPort = mock();
        sut = new QueryUserService(searchUserPort);
    }

    @Test
    @DisplayName("사용자 리스트 조회 서비스 테스트")
    void testQueryUserList() {
        // given
        QueryUserInfoCommand command = new QueryUserInfoCommand(PageRequest.of(1, 10));

        // when
        sut.query(command);

        // then
        verify(searchUserPort, times(1)).search(any());
    }

}