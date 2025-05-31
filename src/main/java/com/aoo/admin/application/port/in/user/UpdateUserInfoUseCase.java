package com.aoo.admin.application.port.in.user;

import com.aoo.common.application.port.in.MessageDto;

public interface UpdateUserInfoUseCase {
    MessageDto updateUserInfo(Long userId, UpdateUserInfoCommand command);
}
