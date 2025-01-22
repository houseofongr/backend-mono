package com.hoo.aoo.admin.application.port.in.room;

import com.hoo.aoo.common.adapter.in.web.MessageDto;

public interface UpdateRoomInfoUseCase {
    MessageDto update(UpdateRoomInfoCommand command);
}
