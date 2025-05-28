package com.hoo.aoo.admin.application.service.space;

import com.hoo.aoo.admin.application.port.in.space.CreateSpaceCommand;
import com.hoo.aoo.admin.application.port.out.space.CreateSpacePort;
import com.hoo.aoo.admin.application.port.out.space.FindSpacePort;
import com.hoo.aoo.admin.application.port.out.space.SaveSpacePort;
import com.hoo.aoo.admin.domain.universe.space.Space;
import com.hoo.aoo.common.application.port.in.MessageDto;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPublicImageUseCase;
import com.hoo.aoo.file.domain.FileSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateSpaceServiceTest {

    CreateSpaceService sut;

    FindSpacePort findSpacePort = mock();
    UploadPublicImageUseCase uploadPublicImageUseCase = mock();
    CreateSpacePort createSpacePort = mock();
    SaveSpacePort saveSpacePort = mock();

    @BeforeEach
    void init() {
        sut = new CreateSpaceService(findSpacePort, uploadPublicImageUseCase, createSpacePort, saveSpacePort);
    }

    @Test
    @DisplayName("스페이스 정상 생성(이미지 저장 및 스페이스 저장 포트 호출)")
    void createSpaceService() {
        // given
        MockMultipartFile basicImage = new MockMultipartFile("image", "image.png", "image/png", "basic image".getBytes());
        CreateSpaceCommand command = new CreateSpaceCommand(1L, -1L, "공간", null, 1f, 1f, 1f, 1f, basicImage);
        Space space = MockEntityFactoryService.getParentSpace();

        // when
        when(uploadPublicImageUseCase.publicUpload((List<MultipartFile>) any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(1L, null, "image.png", "image1234.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS))));
        when(findSpacePort.loadSingle(-1L)).thenReturn(Optional.ofNullable(space));
        MessageDto message = sut.create(command);

        // then
        verify(saveSpacePort, times(1)).save(any(), any());
        assertThat(message.message()).contains("번 스페이스가 생성되었습니다.");
    }
}