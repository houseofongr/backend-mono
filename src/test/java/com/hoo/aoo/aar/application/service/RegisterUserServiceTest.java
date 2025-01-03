package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.SaveUserPort;
import com.hoo.aoo.aar.domain.SnsAccount;
import com.hoo.aoo.aar.domain.SnsAccountF;
import com.hoo.aoo.aar.domain.UserF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RegisterUserServiceTest {

    RegisterUserService sut;
    LoadSnsAccountPort loadSnsAccountPort;
    SaveUserPort saveUserPort;

    JwtUtil jwtUtil;

    @BeforeEach
    void init() {
        loadSnsAccountPort = mock(LoadSnsAccountPort.class);
        saveUserPort = mock(SaveUserPort.class);
        jwtUtil = mock(JwtUtil.class);
        sut = new RegisterUserService(loadSnsAccountPort, saveUserPort, jwtUtil);
    }

    @Test
    @DisplayName("사용자 등록 테스트")
    void testRegisterUser() {
        // given
        RegisterUserCommand.In command = new RegisterUserCommand.In(1L, true, true);

        // when
        when(loadSnsAccountPort.load(1L)).thenReturn(SnsAccountF.KAKAO_NOT_REGISTERED.get());
        when(saveUserPort.save(any())).thenReturn(UserF.REGISTERED_WITH_NO_ID.get());
        RegisterUserCommand.Out register = sut.register(command);

        // then
        verify(loadSnsAccountPort, times(1)).load(1L);
        verify(saveUserPort, times(1)).save(any());
        verify(jwtUtil, times(1)).getAccessToken((SnsAccount) any());

        assertThat(register.nickname()).isEqualTo("leaf");
    }
}