package com.aoo.admin.application.service.space;

import com.aoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.aoo.admin.application.port.out.space.FindSpacePort;
import com.aoo.admin.application.port.out.space.UpdateSpacePort;
import com.aoo.admin.domain.universe.space.Space;
import com.aoo.common.application.port.in.MessageDto;
import com.aoo.common.application.service.MockEntityFactoryService;
import com.aoo.common.domain.Authority;
import com.aoo.file.application.port.in.DeleteFileUseCase;
import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.in.UploadPublicImageUseCase;
import com.aoo.file.domain.FileSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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
    void testUpdateDetailSpaceService() {
        // given
        UpdateSpaceCommand command = new UpdateSpaceCommand( "블랙홀", "블랙홀은 빛도 빨아들입니다.", 0.1f, 0.2f, 0.3f, 0.4f);
        Space space = MockEntityFactoryService.getParentSpace();

        // when
        when(findSpacePort.loadSingle(1L)).thenReturn(Optional.ofNullable(space));
        sut.updateDetail(1L, command);

        // then
        verify(updateSpacePort, times(1)).update(any());
    }

    @Test
    @DisplayName("내부이미지 수정 서비스")
    void updateDetailInnerImage() {
        // given
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "space_inner_image.png", "image/png", "image file".getBytes());
        Space space = MockEntityFactoryService.getParentSpace();

        // when
        when(findSpacePort.loadSingle(space.getId())).thenReturn(Optional.ofNullable(space));
        when(uploadPublicImageUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "space_inner_image.png", "test1235.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));

        sut.updateInnerImage(space.getId(), innerImage);

        // then
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateSpacePort, times(1)).update(space);
    }
}