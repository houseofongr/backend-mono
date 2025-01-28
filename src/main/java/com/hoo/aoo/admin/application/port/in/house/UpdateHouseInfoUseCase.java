package com.hoo.aoo.admin.application.port.in.house;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface UpdateHouseInfoUseCase {
    MessageDto update(UpdateHouseInfoCommand command);
}
