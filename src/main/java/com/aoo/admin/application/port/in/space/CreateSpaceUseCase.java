package com.aoo.admin.application.port.in.space;

import com.aoo.common.application.port.in.MessageDto;

public interface CreateSpaceUseCase {
    CreateSpaceResult create(CreateSpaceCommand command);
}
