package com.hoo.main.application.port.in.authn;

public interface CreateEmailAuthnCodeUseCase {
    CreateEmailAuthnCodeResult create(String email);
}
