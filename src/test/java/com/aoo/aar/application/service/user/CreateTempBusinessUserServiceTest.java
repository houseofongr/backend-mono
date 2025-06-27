package com.aoo.aar.application.service.user;

import com.aoo.aar.application.port.in.user.CreateTempBusinessUserCommand;
import com.aoo.aar.application.port.in.user.CreateTempBusinessUserResult;
import com.aoo.aar.application.port.out.cache.LoadEmailAuthnStatePort;
import com.aoo.aar.application.port.out.persistence.user.CreateTempUserPort;
import com.aoo.aar.application.port.out.persistence.user.SaveTempUserPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateTempBusinessUserServiceTest {

    LoadEmailAuthnStatePort loadEmailAuthnStatePort = mock();
    PasswordEncoder passwordEncoder = mock();
    SaveTempUserPort saveTempUserPort = mock();

    CreateTempBusinessUserService sut = new CreateTempBusinessUserService(loadEmailAuthnStatePort, passwordEncoder, saveTempUserPort);

    @Test
    @DisplayName("임시 비즈니스 회원 생성 서비스")
    void createTempBusinessUserService() {
        // given
        CreateTempBusinessUserCommand command = new CreateTempBusinessUserCommand("test@example.com", "test2143@", "temp_user123");

        // when
        when(loadEmailAuthnStatePort.loadAuthenticated(command.email())).thenReturn(true);
        when(saveTempUserPort.save(anyString(), any(), anyString())).thenReturn(1L);
        CreateTempBusinessUserResult result = sut.create(command);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 임시 사용자가 생성되었습니다. 관리자 승인 후 계정이 등록됩니다.");
        assertThat(result.tempUserId()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo(command.email());
        assertThat(result.nickname()).isEqualTo(command.nickname());
    }

}