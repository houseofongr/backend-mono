package com.hoo.aoo.aar.application.port.in.home;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface UpdateHomeNameUseCase {
    MessageDto updateHomeName(Long userId, Long homeId, String homeName);
}
