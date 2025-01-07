package com.hoo.aoo.aar.application.service;

import com.hoo.aoo.aar.adapter.in.web.authn.security.jwt.JwtUtil;
import com.hoo.aoo.aar.adapter.out.persistence.mapper.UserMapper;
import com.hoo.aoo.aar.application.port.in.RegisterUserCommand;
import com.hoo.aoo.aar.application.port.in.RegisterUserResult;
import com.hoo.aoo.aar.application.port.out.database.LoadSnsAccountPort;
import com.hoo.aoo.aar.application.port.out.database.SaveUserPort;
import com.hoo.aoo.aar.domain.SnsAccountF;
import com.hoo.aoo.aar.domain.account.SnsAccount;
import com.hoo.aoo.aar.domain.exception.InvalidPhoneNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RegisterUserServiceTest {

    RegisterUserService sut;
    LoadSnsAccountPort loadSnsAccountPort;
    SaveUserPort saveUserPort;
    UserMapper userMapper;
    JwtUtil jwtUtil;

    @BeforeEach
    void init() {
        loadSnsAccountPort = mock(LoadSnsAccountPort.class);
        saveUserPort = mock(SaveUserPort.class);
        userMapper = new UserMapper();
        jwtUtil = mock(JwtUtil.class);
        sut = new RegisterUserService(loadSnsAccountPort, saveUserPort, jwtUtil);
    }

    @Test
    @DisplayName("사용자 등록 테스트")
    void testRegisterWithDefaultPhoneNumberUser() throws InvalidPhoneNumberException {
        // given
        RegisterUserCommand command = new RegisterUserCommand(1L, true, true);

        // when
        when(loadSnsAccountPort.load(1L)).thenReturn(Optional.of(SnsAccountF.NOT_REGISTERED_KAKAO.get()));
        RegisterUserResult register = sut.register(command);

        // then
        verify(loadSnsAccountPort, times(1)).load(1L);
        verify(saveUserPort, times(1)).save(any());
        verify(jwtUtil, times(1)).getAccessToken((SnsAccount) any());

        assertThat(register.nickname()).isEqualTo("leaf");
    }
}