package com.hoo.aoo.aar.application.port.in;

import com.hoo.aoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aoo.aar.application.port.in.RegisterUserUseCase;

public class MockRegisterUserUseCase implements RegisterUserUseCase {

    @Override
    public RegisterUserCommand.Out register(RegisterUserCommand.In command) {
        return new RegisterUserCommand.Out("leaf", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
    }
}