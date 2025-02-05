package com.hoo.aoo.admin.application.port.in.user;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface UpdateUserInfoUseCase {
    MessageDto updateUserInfo(Long userId, UpdateUserInfoCommand command);
}
