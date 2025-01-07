package com.hoo.aoo.aar.adapter.out.persistence;

import com.hoo.aoo.aar.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.adapter.out.persistence.repository.UserJpaRepository;
import com.hoo.aoo.aar.domain.DomainFixtureRepository;
import com.hoo.aoo.aar.domain.SnsAccountF;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({UserPersistenceAdapter.class, UserMapper.class})
class UserPersistenceAdapterTest {

    @Autowired
    UserPersistenceAdapter sut;

    @Autowired
    UserJpaRepository repository;

    @Autowired
    UserMapper userMapper;

    @Test
    @Sql("UserPersistenceAdapterTest.sql")
    @DisplayName("사용자 저장")
    void testSaveUser() throws InvalidPhoneNumberException {
        // given
        SnsAccount snsAccount = SnsAccountF.REGISTERED_KAKAO.get();
        User user = DomainFixtureRepository.getRegisteredUser(snsAccount);
        List<SnsAccountJpaEntity> snsAccountJpaEntities = List.of(userMapper.mapToNewJpaEntity(snsAccount));

        // when
        sut.save(user);

        // then
        Optional<UserJpaEntity> optional = repository.findByPhoneNumber(user.getPhoneNumber().getNumber());
        assertThat(optional).isNotEmpty()
                .get().usingRecursiveComparison()
                .ignoringFields("id", "snsAccountEntities", "createdTime", "updatedTime")
                .isEqualTo(userMapper.mapToNewJpaEntity(user, snsAccountJpaEntities));
    }
}