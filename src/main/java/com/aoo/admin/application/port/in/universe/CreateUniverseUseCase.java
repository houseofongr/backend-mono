package com.aoo.admin.application.port.in.universe;

import com.aoo.common.application.port.in.MessageDto;

public interface CreateUniverseUseCase {
    MessageDto create(CreateUniverseCommand command);
}
