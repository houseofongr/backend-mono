package com.hoo.aoo.admin.application.service.space;

import com.hoo.aoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.hoo.aoo.admin.application.port.out.space.FindSpacePort;
import com.hoo.aoo.admin.application.port.out.space.UpdateSpacePort;
import com.hoo.aoo.admin.domain.universe.space.Space;
import com.hoo.aoo.common.application.port.in.MessageDto;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.DeleteFileUseCase;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPublicImageUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UpdateSpaceServiceTest {
    
    UpdateSpaceService sut;

    FindSpacePort findSpacePort = mock();
    UploadPublicImageUseCase uploadPublicImageUseCase = mock();
    DeleteFileUseCase deleteFileUseCase = mock();
    UpdateSpacePort updateSpacePort = mock();

    @BeforeEach
    void init() {
        sut = new UpdateSpaceService(findSpacePort, uploadPublicImageUseCase, deleteFileUseCase, updateSpacePort);
    }

    @Test
    @DisplayName("스페이스 수정 서비스")
    void testUpdateSpaceService() {
        // given
        UpdateSpaceCommand command = new UpdateSpaceCommand( "블랙홀", "블랙홀은 빛도 빨아들입니다.", 0.1f, 0.2f, 0.3f, 0.4f);
        Space space = MockEntityFactoryService.getParentSpace();

        // when
        when(findSpacePort.loadSingle(1L)).thenReturn(Optional.ofNullable(space));
        MessageDto message = sut.update(1L, command);

        // then
        verify(updateSpacePort, times(1)).update(any());
        assertThat(message.message()).contains("번 스페이스가 수정되었습니다.");
    }
}