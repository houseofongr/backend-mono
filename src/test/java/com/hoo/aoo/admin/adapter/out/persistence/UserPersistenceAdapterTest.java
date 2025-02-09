package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.SnsAccountMapper;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.admin.domain.user.DeletedUser;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.adapter.out.persistence.entity.DeletedUserJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.repository.DeletedUserJpaRepository;
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

import java.util.Optional;

import static com.hoo.aoo.admin.domain.user.snsaccount.SnsDomain.KAKAO;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({UserPersistenceAdapter.class, UserMapper.class, SnsAccountMapper.class})
@Sql("UserPersistenceAdapterTest.sql")
class UserPersistenceAdapterTest {

    @Autowired
    UserPersistenceAdapter sut;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private DeletedUserJpaRepository deletedUserJpaRepository;

    @Test
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

    @Test
    @DisplayName("사용자 정보 업데이트 테스트")
    void testUserInfoUpdate() {
        // given
        User user = sut.loadUser(1L).get();
        user.updateNickname("leaf2");

        // when
        sut.updateUser(user);

        // then
        Optional<UserJpaEntity> optional = userJpaRepository.findById(user.getUserId().getId());
        assertThat(optional).isPresent();
        assertThat(optional.get().getNickname()).isEqualTo("leaf2");
    }

    @Test
    @DisplayName("회원탈퇴자 정보 저장 테스트")
    void testSaveDeletedUser() {
        // given
        User user = sut.loadUser(1L).get();
        DeletedUser deletedUser = DeletedUser.create(1L, user, true, true);

        // when
        Long deletedUserId = sut.saveDeletedUser(deletedUser);

        // then
        assertThat(deletedUserId).isNotNull();

        DeletedUserJpaEntity deletedUserJpaEntity = deletedUserJpaRepository.findById(deletedUserId).get();
        assertThat(deletedUserJpaEntity.getNickname()).isEqualTo(user.getUserInfo().getNickname());
        assertThat(deletedUserJpaEntity.getTermsOfDeletionAgreement()).isTrue();
        assertThat(deletedUserJpaEntity.getPersonalInformationDeletionAgreement()).isTrue();
    }

}