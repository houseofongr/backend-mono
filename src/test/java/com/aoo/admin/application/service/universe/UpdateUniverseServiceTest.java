package com.aoo.admin.application.service.universe;

import com.aoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.aoo.admin.application.port.out.universe.FindUniversePort;
import com.aoo.admin.application.port.out.universe.UpdateUniversePort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.universe.Category;
import com.aoo.admin.domain.universe.PublicStatus;
import com.aoo.admin.domain.universe.Universe;
import com.aoo.common.application.service.MockEntityFactoryService;
import com.aoo.common.domain.Authority;
import com.aoo.file.application.port.in.DeleteFileUseCase;
import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.in.UploadPublicAudioUseCase;
import com.aoo.file.application.port.in.UploadPublicImageUseCase;
import com.aoo.file.domain.FileSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUniverseServiceTest {

    UpdateUniverseService sut;

    FindUniversePort findUniversePort = mock();
    UploadPublicImageUseCase uploadPublicImageUseCase = mock();
    UploadPublicAudioUseCase uploadPublicAudioUseCase = mock();
    DeleteFileUseCase deleteFileUseCase = mock();
    UpdateUniversePort updateUniversePort = mock();


    @BeforeEach
    void init() {
        sut = new UpdateUniverseService(findUniversePort, uploadPublicImageUseCase, uploadPublicAudioUseCase, deleteFileUseCase, updateUniversePort);
    }

    @Test
    @DisplayName("정보 수정")
    void testUpdateDetail() {
        // given
        UpdateUniverseCommand command = new UpdateUniverseCommand("오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", 1L, Category.LIFE, PublicStatus.PRIVATE, List.of("오르트구름", "태양계", "윤하", "별"));
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(Optional.ofNullable(universe));
        sut.updateDetail(universe.getId(), command);

        // then
        verify(updateUniversePort, times(1)).updateDetail(universe);
        assertThat(universe.getBasicInfo().getTitle()).isEqualTo("오르트구름");
        assertThat(universe.getBasicInfo().getDescription()).isEqualTo("오르트구름은 태양계 최외곽에 위치하고 있습니다.");
        assertThat(universe.getBasicInfo().getCategory()).isEqualTo(Category.LIFE);
        assertThat(universe.getBasicInfo().getPublicStatus()).isEqualTo(PublicStatus.PRIVATE);
        assertThat(universe.getSocialInfo().getHashtags()).contains("오르트구름", "태양계", "윤하", "별");
    }

    @Test
    @DisplayName("수정 전 파일 확인")
    void testFile() {
        // given
        byte[] over2MB = new byte[2 * 1024 * 1024 + 1];
        byte[] over100MB = new byte[100 * 1024 * 1024 + 1];
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", over2MB);
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_thumb.mp3", "audio/mpeg", over2MB);
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "universe_image.png", "image/png", over100MB);
        assertThatThrownBy(() -> sut.updateThumbnail(1L, null)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> sut.updateThumbMusic(1L, null)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> sut.updateInnerImage(1L, null)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> sut.updateThumbnail(1L, thumbnail)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());
        assertThatThrownBy(() -> sut.updateThumbMusic(1L, thumbMusic)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());
        assertThatThrownBy(() -> sut.updateInnerImage(1L, innerImage)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());
    }

    @Test
    @DisplayName("썸네일 수정 서비스")
    void updateDetailThumbnail() {
        // given
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "image file".getBytes());
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(Optional.ofNullable(universe));
        when(uploadPublicImageUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "universe_thumb.png", "test1235.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));
        sut.updateThumbnail(universe.getId(), thumbnail);

        // then
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateUniversePort, times(1)).updateThumbnail(universe);
    }

    @Test
    @DisplayName("썸뮤직 수정 서비스")
    void updateDetailThumbmusic() {
        // given
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_thumb.mp3", "audio/mpeg", "image file".getBytes());
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(Optional.ofNullable(universe));
        when(uploadPublicAudioUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "universe_music.mp3", "test1235.mp3", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));
        sut.updateThumbMusic(universe.getId(), thumbMusic);

        // then
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateUniversePort, times(1)).updateThumbMusic(universe);
    }

    @Test
    @DisplayName("내부이미지 수정 서비스")
    void updateDetailInnerImage() {
        // given
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "universe_inner_image.png", "image/png", "image file".getBytes());
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(Optional.ofNullable(universe));
        when(uploadPublicImageUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "universe_inner_image.png", "test1235.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));
        sut.updateInnerImage(universe.getId(), innerImage);

        // then
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateUniversePort, times(1)).updateInnerImage(universe);
    }
}