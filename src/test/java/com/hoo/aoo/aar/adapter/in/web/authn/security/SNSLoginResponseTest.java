package com.hoo.aoo.aar.adapter.in.web.authn.security;

import com.hoo.aoo.common.adapter.out.persistence.entity.SnsAccountJpaEntity;
import com.hoo.aoo.common.adapter.out.persistence.entity.UserJpaEntity;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.domain.DomainFixtureRepository;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SNSLoginResponseTest {

    UserMapper userMapper = new UserMapper();

    @Test
    @DisplayName("사용자 등록 여부에 따른 닉네임 테스트")
    void testBeforeRegister() throws InvalidPhoneNumberException {
        SnsAccountJpaEntity registeredSnsAccount = userMapper.mapToNewJpaEntity(DomainFixtureRepository.getRegisteredSnsAccount());

        SNSLoginResponse notRegisteredResponse = SNSLoginResponse.of(registeredSnsAccount, null, true);

        UserJpaEntity entity = mock(UserJpaEntity.class);
        when(entity.getNickname()).thenReturn("LEAF");
        registeredSnsAccount.setUserEntity(entity);
        SNSLoginResponse registeredResponse = SNSLoginResponse.of(registeredSnsAccount, null, false);

        assertThat(notRegisteredResponse.getAttributes()).extractingByKey("nickname").isEqualTo("leaf");
        assertThat(registeredResponse.getAttributes()).extractingByKey("nickname").isEqualTo("LEAF");
    }
}