package com.hoo.aoo.admin.application.service.universe;

import com.hoo.aoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.hoo.aoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.aoo.admin.application.port.out.universe.UpdateUniversePort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.universe.Category;
import com.hoo.aoo.admin.domain.universe.PublicStatus;
import com.hoo.aoo.admin.domain.universe.Universe;
import com.hoo.aoo.common.application.port.in.MessageDto;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.DeleteFileUseCase;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPublicAudioUseCase;
import com.hoo.aoo.file.application.port.in.UploadPublicImageUseCase;
import com.hoo.aoo.file.domain.FileSize;
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
    void testUpdate() {
        // given
        UpdateUniverseCommand command = new UpdateUniverseCommand( "오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", Category.LIFE, PublicStatus.PRIVATE, List.of("오르트구름", "태양계", "윤하", "별"));
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(Optional.ofNullable(universe));

        MessageDto message = sut.update(universe.getId(), command);

        // then
        verify(updateUniversePort, times(1)).update(universe);
        assertThat(message.message()).contains("번 유니버스가 수정되었습니다.");
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
        byte[] over5MB = new byte[5 * 1024 * 1024 + 1];
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", over2MB);
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_thumb.mp3", "audio/mpeg", over2MB);
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "universe_image.png", "image/png", over5MB);
        assertThatThrownBy(() -> sut.updateThumbnail(1L, null)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> sut.updateThumbMusic(1L, null)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> sut.updateInnerImage(1L, null)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> sut.updateThumbnail(1L, thumbnail)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.EXCEEDED_UNIVERSE_FILE_SIZE.getMessage());
        assertThatThrownBy(() -> sut.updateThumbMusic(1L, thumbMusic)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.EXCEEDED_UNIVERSE_FILE_SIZE.getMessage());
        assertThatThrownBy(() -> sut.updateInnerImage(1L, innerImage)).isInstanceOf(AdminException.class).hasMessage(AdminErrorCode.EXCEEDED_UNIVERSE_FILE_SIZE.getMessage());
    }

    @Test
    @DisplayName("썸네일 수정 서비스")
    void updateThumbnail() {
        // given
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "image file".getBytes());
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(Optional.ofNullable(universe));
        when(uploadPublicImageUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "universe_music.mp3", "test1235.mp3", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));

        MessageDto message = sut.updateThumbnail(universe.getId(), thumbnail);

        // then
        assertThat(message.message()).contains("번 유니버스의 썸네일이 수정되었습니다.");
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateUniversePort, times(1)).update(universe);
    }

    @Test
    @DisplayName("썸뮤직 수정 서비스")
    void updateThumbmusic() {
        // given
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_thumb.mp3", "audio/mpeg", "image file".getBytes());
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(Optional.ofNullable(universe));
        when(uploadPublicAudioUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "universe_music.mp3", "test1235.mp3", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));

        MessageDto message = sut.updateThumbMusic(universe.getId(), thumbMusic);

        // then
        assertThat(message.message()).contains("번 유니버스의 썸뮤직이 수정되었습니다.");
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateUniversePort, times(1)).update(universe);
    }

    @Test
    @DisplayName("내부이미지 수정 서비스")
    void updateInnerImage() {
        // given
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "universe_inner_image.png", "image/png", "image file".getBytes());
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(universe.getId())).thenReturn(Optional.ofNullable(universe));
        when(uploadPublicImageUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(12L, null, "universe_music.mp3", "test1235.mp3", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));

        MessageDto message = sut.updateInnerImage(universe.getId(), innerImage);

        // then
        assertThat(message.message()).contains("번 유니버스의 내부 이미지가 수정되었습니다.");
        verify(deleteFileUseCase, times(1)).deleteFile(anyLong());
        verify(updateUniversePort, times(1)).update(universe);
    }
}