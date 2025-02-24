package com.hoo.aoo.aar.application.service.home;

import com.hoo.aoo.aar.application.port.out.persistence.home.CheckOwnerPort;
import com.hoo.aoo.aar.application.service.AarErrorCode;
import com.hoo.aoo.admin.application.port.in.home.UpdateHomeUseCase;
import com.hoo.aoo.common.application.port.in.MessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateHomeNameServiceTest {

    UpdateHomeNameService sut;

    CheckOwnerPort checkOwnerPort;
    UpdateHomeUseCase updateHomeUseCase;


    @BeforeEach
    void init() {
        checkOwnerPort = mock();
        updateHomeUseCase = mock();
        sut = new UpdateHomeNameService(checkOwnerPort, updateHomeUseCase);
    }

    @Test
    @DisplayName("홈 이름 수정 서비스 테스트")
    void testUpdateHomeNameService() {
        // given
        Long userId = 1L;
        Long notOwnedUserId = 2L;
        Long homeId = 1L;
        String newName = "new name";

        // when
        when(checkOwnerPort.checkHome(userId, homeId)).thenReturn(true);
        when(checkOwnerPort.checkHome(notOwnedUserId, homeId)).thenReturn(false);
        MessageDto message = sut.updateHomeName(userId, homeId, newName);

        // then
        assertThatThrownBy(() -> sut.updateHomeName(notOwnedUserId, homeId, newName)).hasMessage(AarErrorCode.NOT_OWNED_HOME.getMessage());
        assertThat(message.message()).isEqualTo("1번 홈의 이름이 수정되었습니다.");
    }

}