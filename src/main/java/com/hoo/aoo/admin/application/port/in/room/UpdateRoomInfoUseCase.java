package com.hoo.aoo.admin.application.port.in.room;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface UpdateRoomInfoUseCase {
    MessageDto update(UpdateRoomInfoCommand command);
}
