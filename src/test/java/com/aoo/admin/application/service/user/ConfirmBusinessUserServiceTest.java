package com.aoo.admin.application.service.user;

import com.aoo.admin.application.port.in.user.ConfirmBusinessUserResult;
import com.aoo.admin.application.port.out.user.FindBusinessUserPort;
import com.aoo.admin.application.port.out.user.SaveUserPort;
import com.aoo.admin.domain.user.BusinessUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ConfirmBusinessUserServiceTest {

    FindBusinessUserPort findBusinessUserPort = mock();
    SaveUserPort saveUserPort = mock();

    ConfirmBusinessUserService sut = new ConfirmBusinessUserService(findBusinessUserPort, saveUserPort);

    @Test
    @DisplayName("비즈니스 회원가입 승인 서비스")
    void testConfirmBusinessUserService() {
        // given
        Long tempUserId = 1L;
        BusinessUser businessUser = BusinessUser.create(1L, "leaf", "test@example.com", true, true);

        // when
        when(findBusinessUserPort.findBusinessUser(tempUserId)).thenReturn(businessUser);
        when(saveUserPort.saveBusinessUser(any())).thenReturn(1L);
        ConfirmBusinessUserResult result = sut.confirm(tempUserId);

        // then
        assertThat(result.message()).matches("비즈니스 사용자가 등록되어 \\[#\\d+]번 사용자가 생성되었습니다.");
        assertThat(result.userId()).isEqualTo(1L);
        assertThat(result.email()).isEqualTo("test@example.com");
        assertThat(result.nickname()).isEqualTo("leaf");
    }

}