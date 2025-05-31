package com.aoo.admin.application.port.in.house;

import com.aoo.common.application.port.in.MessageDto;

public interface DeleteHouseUseCase {
    MessageDto deleteHouse(Long id);
}
