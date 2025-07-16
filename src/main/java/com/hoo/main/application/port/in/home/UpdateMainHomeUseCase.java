package com.hoo.main.application.port.in.home;

import com.hoo.common.application.port.in.MessageDto;

public interface UpdateMainHomeUseCase {
    MessageDto updateMainHome(Long userId, Long homeId);
}
