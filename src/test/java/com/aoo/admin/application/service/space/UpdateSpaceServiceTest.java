package com.aoo.admin.application.service.space;

import com.aoo.admin.application.port.in.space.UpdateSpaceCommand;
import com.aoo.admin.application.port.in.space.UpdateSpaceResult;
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

import static org.assertj.core.api.Assertions.*;
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
    @DisplayName("스페이스 기본정보 수정 서비스")
    void testUpdateDetailSpaceService() {
        // given
        UpdateSpaceCommand.Detail command = new UpdateSpaceCommand.Detail( "블랙홀", "블랙홀은 빛도 빨아들입니다.");
        Space space = MockEntityFactoryService.getParentSpace();

        // when
        when(findSpacePort.loadSingle(1L)).thenReturn(Optional.ofNullable(space));
        UpdateSpaceResult.Detail result = sut.updateDetail(1L, command);

        // then
        verify(updateSpacePort, times(1)).update(any());
        assertThat(result.title()).isEqualTo(command.title());
        assertThat(result.description()).isEqualTo(command.description());
        assertThat(result.message()).matches("\\[#-?\\d+]번 스페이스의 상세정보가 수정되었습니다.");
    }

    @Test
    @DisplayName("스페이스 좌표 수정 서비스")
    void testUpdatePositionSpaceService() {
        // given
        UpdateSpaceCommand.Position command = new UpdateSpaceCommand.Position( 0.3f, 0.4f, 0.5f, 0.6f);
        Space space = MockEntityFactoryService.getParentSpace();

        // when
        when(findSpacePort.loadSingle(1L)).thenReturn(Optional.ofNullable(space));
        UpdateSpaceResult.Position result = sut.updatePosition(1L, command);

        // then
        verify(updateSpacePort, times(1)).update(any());
        assertThat(result.startX()).isEqualTo(command.startX());
        assertThat(result.startY()).isEqualTo(command.startY());
        assertThat(result.endX()).isEqualTo(command.endX());
        assertThat(result.endY()).isEqualTo(command.endY());
        assertThat(result.message()).matches("\\[#-?\\d+]번 스페이스의 좌표가 수정되었습니다.");
    }

    @Test
    @DisplayName("내부이미지 수정 서비스")
    void updateDetailInnerImage() {
        // given
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "space_inner_image.png", "image/png", "image file".getBytes());
        Space space = MockEntityFactoryService.getParentSpace();
        Long beforeSpaceInnerImageId = space.getFileInfo().getInnerImageId();

        // when
        when(findSpacePort.loadSingle(space.getId())).thenReturn(Optional.ofNullable(space));
        when(uploadPublicImageUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "space_inner_image.png", "test1235.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));

        UpdateSpaceResult.InnerImage result = sut.updateInnerImage(space.getId(), innerImage);

        // then
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateSpacePort, times(1)).update(space);
        assertThat(result.newInnerImageId()).isEqualTo(12L);
        assertThat(result.deletedInnerImageId()).isEqualTo(beforeSpaceInnerImageId);
        assertThat(result.message()).matches("\\[#-?\\d+]번 스페이스의 내부이미지가 수정되었습니다.");
    }
}