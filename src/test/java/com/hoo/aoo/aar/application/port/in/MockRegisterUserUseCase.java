package com.hoo.aoo.aar.application.port.in;

public class MockRegisterUserUseCase implements RegisterUserUseCase {

    @Override
    public RegisterUserResult register(RegisterUserCommand command) {
        return new RegisterUserResult("leaf", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
    }
}