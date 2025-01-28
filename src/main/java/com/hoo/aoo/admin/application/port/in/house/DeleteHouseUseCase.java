package com.hoo.aoo.admin.application.port.in.house;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface DeleteHouseUseCase {
    MessageDto deleteHouse(Long id);
}
