package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.mapper.AdminUserMapper;
import com.hoo.aoo.common.adapter.out.persistence.repository.UserQueryDslRepositoryImpl;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain.KAKAO;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AdminUserPersistenceAdapter.class, AdminUserMapper.class, UserQueryDslRepositoryImpl.class})
class AdminUserPersistenceAdapterTest {

    @Autowired
    AdminUserPersistenceAdapter sut;

    @Test
    @Sql("AdminUserPersistenceAdapterTest.sql")
    @DisplayName("사용자 검색 기능 테스트")
    void testSearchUser() {
        // given
        QueryUserInfoCommand command = new QueryUserInfoCommand(PageRequest.of(0, 10));

        // when
        QueryUserInfoResult result = sut.search(command);

        // then
        assertThat(result.users()).hasSize(1);
        assertThat(result.users()).anySatisfy(userInfo -> {
                    assertThat(userInfo.id()).isEqualTo(1L);
                    assertThat(userInfo.realName()).isEqualTo("남상엽");
                    assertThat(userInfo.nickName()).isEqualTo("leaf");
                    assertThat(userInfo.phoneNumber()).isEqualTo("010-1234-5678");
                    assertThat(userInfo.registeredDate()).matches("\\d{4}\\.\\d{2}\\.\\d{2}\\.");
                    assertThat(userInfo.snsAccounts().getFirst().domain()).isEqualTo(KAKAO);
                    assertThat(userInfo.snsAccounts().getFirst().email()).isEqualTo("test@example.com");
                }
        );
    }
}