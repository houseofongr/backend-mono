package com.hoo.aoo.admin.application.port.in.universe;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface UpdateUniverseUseCase {
    MessageDto update(UpdateUniverseCommand command);
}
