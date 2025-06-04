package com.aoo.admin.application.port.in.universe;

import com.aoo.common.application.port.in.MessageDto;

public interface CreateUniverseUseCase {
    CreateUniverseResult create(CreateUniverseCommand command);
}
