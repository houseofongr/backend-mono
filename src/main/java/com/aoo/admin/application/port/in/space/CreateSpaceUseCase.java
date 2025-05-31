package com.aoo.admin.application.port.in.space;

import com.aoo.common.application.port.in.MessageDto;

public interface CreateSpaceUseCase {
    MessageDto create(CreateSpaceCommand command);
}
