package com.hoo.aoo.admin.application.port.in.house;

import com.hoo.aoo.common.adapter.in.web.MessageDto;

public interface DeleteRoomUseCase {
    MessageDto delete(Long houseId, String roomName);
}
