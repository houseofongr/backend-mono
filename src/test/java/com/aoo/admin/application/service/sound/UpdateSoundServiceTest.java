package com.aoo.admin.application.service.sound;

import com.aoo.admin.application.port.in.sound.CreateSoundCommand;
import com.aoo.admin.application.port.in.sound.UpdateSoundCommand;
import com.aoo.admin.application.port.in.sound.UpdateSoundResult;
import com.aoo.admin.application.port.out.sound.FindSoundPort;
import com.aoo.admin.application.port.out.sound.UpdateSoundPort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.domain.universe.piece.sound.Sound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UpdateSoundServiceTest {

    FindSoundPort findSoundPort = mock();
    UpdateSoundPort updateSoundPort = mock();
    UpdateSoundService sut = new UpdateSoundService(findSoundPort, updateSoundPort);

    @Test
    @DisplayName("요청 파라미터 테스트")
    void testRequestParameter() {
        String goodTitle = "소리";
        String goodDescription = "사운드는 소리입니다.";
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);
        String length5000 = "a".repeat(5001);

        assertThatThrownBy(() -> new UpdateSoundCommand(emptyTitle, goodDescription)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSoundCommand(blankTitle, goodDescription)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSoundCommand(length100, goodDescription)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateSoundCommand(goodTitle, length5000)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }


    @Test
    @DisplayName("사운드 상세정보 업데이트 서비스")
    void testUpdateSoundDetailService() {
        // given
        Long id = 1L;
        UpdateSoundCommand command = new UpdateSoundCommand("수정", "수정할 내용");
        Sound sound = Sound.create(123L, 345L, 1L, "소리", "사운드는 소리입니다.");

        // when
        when(findSoundPort.find(id)).thenReturn(sound);
        UpdateSoundResult result = sut.updateDetail(id, command);

        // then
        verify(updateSoundPort, times(1)).update(sound);
        assertThat(result.message()).matches("\\[#\\d+]번 사운드의 상세정보가 수정되었습니다.");
        assertThat(result.title()).isEqualTo("수정");
        assertThat(result.description()).isEqualTo("수정할 내용");
    }

}