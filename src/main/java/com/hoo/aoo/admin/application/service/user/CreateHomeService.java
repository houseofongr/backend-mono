package com.hoo.aoo.admin.application.service.user;

import com.hoo.aoo.admin.application.port.in.user.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.user.CreateHomeResult;
import com.hoo.aoo.admin.application.port.in.user.CreateHomeUseCase;
import com.hoo.aoo.admin.application.port.out.user.SaveHomePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateHomeService implements CreateHomeUseCase {

    private final SaveHomePort saveHomePort;

    @Override
    public CreateHomeResult create(CreateHomeCommand command) {
        return saveHomePort.save(command);
    }
}
