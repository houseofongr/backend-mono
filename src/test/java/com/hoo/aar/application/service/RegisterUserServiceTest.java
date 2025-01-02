package com.hoo.aar.application.service;

import com.hoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aar.adapter.in.web.authn.security.jwt.MockJwtUtil;
import com.hoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aar.application.port.out.database.LoadUserPort;
import com.hoo.aar.application.port.out.database.SaveUserPort;
import com.hoo.aar.domain.SnsAccountF;
import com.hoo.aar.domain.User;
import com.hoo.aar.domain.UserF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterUserServiceTest {

    RegisterUserService sut;
    LoadUserPort loadUserPort;
    LoadSnsAccountPort loadSnsAccountPort;
    SaveUserPort saveUserPort;

    JwtUtil jwtUtil;

    @BeforeEach
    void init() {
        loadUserPort = mock(LoadUserPort.class);
        loadSnsAccountPort = mock(LoadSnsAccountPort.class);
        saveUserPort = mock(SaveUserPort.class);
        jwtUtil = mock(JwtUtil.class);
        sut = new RegisterUserService(loadUserPort, loadSnsAccountPort, saveUserPort, null, jwtUtil);
    }

    @Test
    @DisplayName("사용자 등록 테스트")
    void testRegisterUser() {
        // given
        RegisterUserCommand.In command = new RegisterUserCommand.In(1L, true, true);

        // when
        when(loadSnsAccountPort.load(1L)).thenReturn(SnsAccountF.KAKAO_NOT_REGISTERED.get());
        when(saveUserPort.save(any())).thenReturn(UserF.NOT_REGISTERED.get());
        RegisterUserCommand.Out register = sut.register(command);

        // then
        verify(loadSnsAccountPort, times(1)).load(1L);
        verify(saveUserPort, times(1)).save(any());
        verify(jwtUtil, times(1)).getAccessToken(any(), any());

        assertThat(register.nickname()).isEqualTo("leaf");
    }
}