package com.hoo.aoo.aar.application.service.user;

import com.hoo.aoo.aar.application.port.in.user.DeleteMyInfoUseCase;
import com.hoo.aoo.aar.application.port.out.api.UnlinkKakaoLogin;
import com.hoo.aoo.admin.application.port.in.user.DeleteUserCommand;
import com.hoo.aoo.admin.application.port.in.user.DeleteUserUseCase;
import com.hoo.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteMyInfoService implements DeleteMyInfoUseCase {

    private final UnlinkKakaoLogin unlinkKakaoLogin;
    private final DeleteUserUseCase deleteUserUseCase;

    @Override
    public MessageDto deleteMyInfo(Long userId, DeleteUserCommand command) {
        unlinkKakaoLogin.unlink(userId);
        return deleteUserUseCase.deleteUser(userId, command);
    }
}
