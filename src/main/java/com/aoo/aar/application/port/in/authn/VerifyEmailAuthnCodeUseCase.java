package com.aoo.aar.application.port.in.authn;

public interface VerifyEmailAuthnCodeUseCase {
    VerifyEmailAuthnCodeResult verify(String email, String code);
}
