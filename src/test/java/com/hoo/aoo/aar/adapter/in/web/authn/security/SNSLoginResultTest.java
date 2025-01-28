package com.hoo.aoo.aar.adapter.in.web.authn.security;

import com.hoo.aoo.aar.application.port.in.authn.SNSLoginResult;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import com.hoo.aoo.aar.domain.user.User;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsDomain;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SNSLoginResultTest {

    @Test
    @DisplayName("사용자 등록 여부에 따른 닉네임 테스트")
    void testBeforeRegister() throws InvalidPhoneNumberException {
        SnsAccount snsAccount = MockEntityFactoryService.getSnsAccount();

        SNSLoginResult notRegisteredResponse = SNSLoginResult.from(snsAccount, null);

        User user = User.load(1L, "남상엽", "LEAF", true, true, null,null , null);

        SNSLoginResult registeredResponse = SNSLoginResult.from(user, null, SnsDomain.KAKAO);

        assertThat(notRegisteredResponse.getAttributes()).extractingByKey("nickname").isEqualTo("leaf");
        assertThat(registeredResponse.getAttributes()).extractingByKey("nickname").isEqualTo("LEAF");
    }
}