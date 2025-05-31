package com.aoo.admin.application.port.in.house;

import com.aoo.common.application.port.in.MessageDto;

public interface UpdateHouseInfoUseCase {
    MessageDto update(UpdateHouseInfoCommand command);
}
