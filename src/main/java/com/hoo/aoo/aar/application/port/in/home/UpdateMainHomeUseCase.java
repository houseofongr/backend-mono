package com.hoo.aoo.aar.application.port.in.home;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface UpdateMainHomeUseCase {
    MessageDto updateMainHome(Long userId, Long homeId);
}
