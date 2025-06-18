package com.aoo.aar.application.service.authn;

import com.aoo.aar.application.port.out.cache.LoadEmailAuthnCodePort;
import com.aoo.aar.application.service.AarErrorCode;
import com.aoo.common.application.port.in.MessageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class VerifyEmailAuthnCodeServiceTest {

    LoadEmailAuthnCodePort loadEmailAuthnCodePort = mock();
    VerifyEmailAuthnCodeService sut = new VerifyEmailAuthnCodeService(loadEmailAuthnCodePort);

    @Test
    @DisplayName("이메일 인증코드 검증 서비스")
    void testEmailCodeVerifyService() {
        // given
        String email = "test@example.com";
        String code = "123456";

        // when

        // 인증번호 다를 경우
        when(loadEmailAuthnCodePort.loadAuthnCodeByEmail(email)).thenReturn("654321");
        assertThatThrownBy(() -> sut.verify(email, code)).hasMessage(AarErrorCode.EMAIL_CODE_AUTHENTICATION_FAILED.getMessage());

        // 정상 인증번호
        when(loadEmailAuthnCodePort.loadAuthnCodeByEmail(email)).thenReturn(code);
        MessageDto result = sut.verify(email, code);

        // then
        assertThat(result.message()).isEqualTo("이메일 인증에 성공했습니다.");
    }

}