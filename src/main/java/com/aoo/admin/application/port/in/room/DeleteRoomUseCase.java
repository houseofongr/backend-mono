package com.aoo.admin.application.port.in.room;

import com.aoo.common.application.port.in.MessageDto;

public interface DeleteRoomUseCase {
    MessageDto deleteRoom(Long roomId);
}
