package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aoo.aar.application.port.in.RegisterUserResult;
import com.hoo.aoo.aar.application.port.out.database.snsaccount.FindSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.user.CreateUserPort;
import com.hoo.aoo.aar.application.port.out.database.user.SaveUserPort;
import com.hoo.aoo.aar.domain.user.snsaccount.SnsAccount;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RegisterUserServiceTest {

    RegisterUserService sut;
    FindSnsAccountPort findSnsAccountPort;
    CreateUserPort createUserPort;
    SaveUserPort saveUserPort;
    JwtUtil jwtUtil;

    @BeforeEach
    void init() {
        findSnsAccountPort = mock();
        createUserPort = mock();
        saveUserPort = mock();
        jwtUtil = mock();
        sut = new RegisterUserService(findSnsAccountPort, createUserPort, saveUserPort, jwtUtil);
    }

    @Test
    @DisplayName("사용자 등록 테스트")
    void testRegisterUser() {
        // given
        RegisterUserCommand command = new RegisterUserCommand(1L, true, true);

        // when
        when(findSnsAccountPort.load(1L)).thenReturn(Optional.of(MockEntityFactoryService.getSnsAccount()));
        when(createUserPort.createUser(any(), any(), any())).thenReturn(MockEntityFactoryService.getUser());
        RegisterUserResult register = sut.register(command);

        // then
        verify(findSnsAccountPort, times(1)).load(1L);
        verify(saveUserPort, times(1)).save(any());
        verify(jwtUtil, times(1)).getAccessToken((SnsAccount) any());

        assertThat(register.nickname()).isEqualTo("leaf");
    }
}