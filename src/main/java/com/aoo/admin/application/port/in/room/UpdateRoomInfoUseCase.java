package com.aoo.admin.application.port.in.room;

import com.aoo.common.application.port.in.MessageDto;

public interface UpdateRoomInfoUseCase {
    MessageDto update(UpdateRoomInfoCommand command);
}
