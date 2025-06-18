package com.aoo.aar.application.service.authn;

import com.aoo.aar.application.port.in.authn.VerifyEmailAuthnCodeUseCase;
import com.aoo.aar.application.port.out.cache.LoadEmailAuthnCodePort;
import com.aoo.aar.application.service.AarErrorCode;
import com.aoo.aar.application.service.AarException;
import com.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyEmailAuthnCodeService implements VerifyEmailAuthnCodeUseCase {

    private final LoadEmailAuthnCodePort loadEmailAuthnCodePort;

    @Override
    public MessageDto verify(String email, String code) {
        String codeInCache = loadEmailAuthnCodePort.loadAuthnCodeByEmail(email);
        if (!codeInCache.equals(code)) throw new AarException(AarErrorCode.EMAIL_CODE_AUTHENTICATION_FAILED);

        return new MessageDto("이메일 인증에 성공했습니다.");
    }
}
