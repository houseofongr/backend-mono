package com.hoo.aar.application.port.in;

public interface RegisterUserUseCase {
    RegisterUserCommand.Out register(RegisterUserCommand.In command);
}
