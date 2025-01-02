package com.hoo.aar.adapter.in.web.authn.security;

import com.hoo.aar.domain.DomainFixtureRepository;
import com.hoo.aar.domain.SnsAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SNSLoginResponseTest {

    @Test
    @DisplayName("사용자 등록 여부에 따른 닉네임 테스트")
    void testBeforeRegister() {
        SnsAccount registeredSnsAccount = DomainFixtureRepository.getRegisteredSnsAccount();

        SNSLoginResponse registeredResponse = SNSLoginResponse.of(registeredSnsAccount, null, false);
        SNSLoginResponse notRegisteredResponse = SNSLoginResponse.of(registeredSnsAccount, null, true);

        assertThat(registeredResponse.getAttributes()).extractingByKey("nickname").isEqualTo("LEAF");
        assertThat(notRegisteredResponse.getAttributes()).extractingByKey("nickname").isEqualTo("leaf");
    }
}