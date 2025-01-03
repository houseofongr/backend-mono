package com.hoo.aoo.aar.application.port.in;

public interface RegisterUserUseCase {
    RegisterUserCommand.Out register(RegisterUserCommand.In command);
}
