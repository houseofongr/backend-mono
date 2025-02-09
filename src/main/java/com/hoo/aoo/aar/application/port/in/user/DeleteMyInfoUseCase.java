package com.hoo.aoo.aar.application.port.in.user;

import com.hoo.aoo.admin.application.port.in.user.DeleteUserCommand;
import com.hoo.aoo.common.application.port.in.MessageDto;

public interface DeleteMyInfoUseCase {
    MessageDto deleteMyInfo(Long userId, DeleteUserCommand command);
}
