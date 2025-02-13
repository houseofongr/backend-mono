package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.repository.SnsAccountJpaRepository;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.SnsAccountMapper;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoCommand;
import com.hoo.aoo.admin.application.port.in.user.QueryUserInfoResult;
import com.hoo.aoo.admin.domain.user.DeletedUser;
import com.hoo.aoo.admin.domain.user.User;
import com.hoo.aoo.common.adapter.out.persistence.entity.*;
import com.hoo.aoo.common.adapter.out.persistence.repository.DeletedUserJpaRepository;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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
    UserJpaRepository userJpaRepository;

    @Autowired
    DeletedUserJpaRepository deletedUserJpaRepository;

    @Autowired
    EntityManager entityManager;

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
                    assertThat(userInfo.id()).isEqualTo(10L);
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
    @DisplayName("사용자 저장")
    void testSaveUser() {
        // given
        User user = MockEntityFactoryService.getUser();

        // when
        Long savedId = sut.save(user);
        Optional<UserJpaEntity> optional = userJpaRepository.findById(savedId);

        // then
        Assertions.assertThat(optional).isNotEmpty();
        Assertions.assertThat(optional.get().getNickname()).isEqualTo(user.getUserInfo().getNickname());
        Assertions.assertThat(optional.get().getRealName()).isEqualTo(user.getUserInfo().getRealName());
        Assertions.assertThat(optional.get().getPhoneNumber()).isNull();
        Assertions.assertThat(optional.get().getPersonalInformationAgreement()).isEqualTo(user.getAgreement().getPersonalInformationAgreement());
        Assertions.assertThat(optional.get().getTermsOfUseAgreement()).isEqualTo(user.getAgreement().getTermsOfUseAgreement());
        Assertions.assertThat(optional.get().getCreatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(1L, ChronoUnit.SECONDS));
        Assertions.assertThat(optional.get().getUpdatedTime()).isCloseTo(ZonedDateTime.now(), new TemporalUnitWithinOffset(1L, ChronoUnit.SECONDS));
    }

    @Test
    @DisplayName("사용자 정보 업데이트 테스트")
    void testUserInfoUpdate() {
        // given
        User user = sut.loadUser(10L).get();
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
        User user = sut.loadUser(10L).get();
        DeletedUser deletedUser = DeletedUser.create(1L, user, true, true);

        // when
        Long deletedUserId = sut.saveDeletedUser(deletedUser);

        // then
        assertThat(deletedUserId).isNotNull();

        DeletedUserJpaEntity deletedUserJpaEntity = deletedUserJpaRepository.findById(deletedUserId).get();
        assertThat(deletedUserJpaEntity.getNickname()).isEqualTo(user.getUserInfo().getMaskedNickname());
        assertThat(deletedUserJpaEntity.getTermsOfDeletionAgreement()).isTrue();
        assertThat(deletedUserJpaEntity.getPersonalInformationDeletionAgreement()).isTrue();
    }

    @Test
    @DisplayName("회원정보 삭제 테스트")
    void testDeleteUserInfo() {
        // given
        User user = sut.loadUser(10L).get();

        // when
        sut.deleteUser(user);

        // then
        assertThat(userJpaRepository.findById(user.getUserId().getId())).isEmpty();
        assertThat(entityManager.find(SnsAccountJpaEntity.class,1L)).isNull();
        assertThat(entityManager.find(SoundSourceJpaEntity.class,1L)).isNull();
        assertThat(entityManager.find(SoundSourceJpaEntity.class,2L)).isNull();
        assertThat(entityManager.find(SoundSourceJpaEntity.class,3L)).isNull();
        assertThat(entityManager.find(ItemJpaEntity.class,1L)).isNull();
        assertThat(entityManager.find(ItemJpaEntity.class,2L)).isNull();
        assertThat(entityManager.find(ItemJpaEntity.class,3L)).isNull();
        assertThat(entityManager.find(ItemShapeRectangleJpaEntity.class,1L)).isNull();
        assertThat(entityManager.find(ItemShapeCircleJpaEntity.class,2L)).isNull();
        assertThat(entityManager.find(ItemShapeEllipseJpaEntity.class,3L)).isNull();
        assertThat(entityManager.find(HomeJpaEntity.class,1L)).isNull();
        assertThat(entityManager.find(HomeJpaEntity.class,2L)).isNull();
    }

}