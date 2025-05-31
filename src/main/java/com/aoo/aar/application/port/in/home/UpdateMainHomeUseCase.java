package com.aoo.aar.application.port.in.home;

import com.aoo.common.application.port.in.MessageDto;

public interface UpdateMainHomeUseCase {
    MessageDto updateMainHome(Long userId, Long homeId);
}
