package com.hoo.aoo.admin.application.service.home;

import com.hoo.aoo.admin.application.port.in.home.CreateHomeCommand;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeResult;
import com.hoo.aoo.admin.application.port.in.home.CreateHomeUseCase;
import com.hoo.aoo.admin.application.port.out.home.SaveHomePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateHomeService implements CreateHomeUseCase {

    private final SaveHomePort saveHomePort;

    @Override
    @Transactional
    public CreateHomeResult create(CreateHomeCommand command) {
        return saveHomePort.save(command);
    }
}
