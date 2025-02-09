package com.hoo.aoo.aar.application.service.user;

import com.hoo.aoo.aar.application.port.out.api.UnlinkKakaoLogin;
import com.hoo.aoo.admin.application.port.in.user.DeleteUserCommand;
import com.hoo.aoo.admin.application.port.in.user.DeleteUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteMyInfoServiceTest {

    DeleteMyInfoService sut;

    UnlinkKakaoLogin unlinkKakaoLogin;
    DeleteUserUseCase deleteUserUseCase;

    @BeforeEach
    void init() {
        unlinkKakaoLogin = mock();
        deleteUserUseCase = mock();
        sut = new DeleteMyInfoService(unlinkKakaoLogin, deleteUserUseCase);
    }

    @Test
    @DisplayName("본인계정 탈퇴 서비스 테스트")
    void testDeleteMyInfo() {
        // given
        Long userId = 10L;
        DeleteUserCommand command = new DeleteUserCommand(true, true);

        // when
        sut.deleteMyInfo(userId, command);

        // then
        verify(unlinkKakaoLogin, times(1)).unlink(userId);
        verify(deleteUserUseCase, times(1)).deleteUser(userId,command);
    }

}