package com.hoo.aoo.admin.application.port.in.space;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface UpdateSpaceUseCase {
    MessageDto update(UpdateSpaceCommand command);
}
