package com.hoo.main.application.port.in.authn;

public interface VerifyEmailAuthnCodeUseCase {
    VerifyEmailAuthnCodeResult verify(String email, String code);
}
