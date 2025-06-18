package com.aoo.aar.application.port.in.authn;

import com.aoo.common.application.port.in.MessageDto;

public interface VerifyEmailAuthnCodeUseCase {
    MessageDto verify(String email, String code);
}
