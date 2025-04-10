package com.hoo.aoo.admin.application.service.universe;

import com.hoo.aoo.admin.application.port.in.universe.UpdateUniverseCommand;
import com.hoo.aoo.admin.application.port.out.universe.FindUniversePort;
import com.hoo.aoo.admin.application.port.out.universe.UpdateUniversePort;
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
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("수정 전 썸네일 혹은 썸뮤직 삭제하기")
    void deleteThumbnailOrThumbMusic() {
        // given
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "image file".getBytes());
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes());
        UpdateUniverseCommand command = new UpdateUniverseCommand(1L, null, null, null, null, null, thumbnail, thumbMusic);

        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(1L)).thenReturn(Optional.ofNullable(universe));
        when(uploadPublicImageUseCase.publicUpload(any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(12L, null, "universe_music.mp3", "test1235.mp3", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS))));
        when(uploadPublicAudioUseCase.publicUpload(any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(101L, null, "universe_thumb.png", "test1234.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS))));
        sut.update(command);

        // then
        verify(deleteFileUseCase, times(2)).deleteFile(anyLong());
    }

    @Test
    @DisplayName("정상 수정로직(Happy Case)")
    void happyCase() {
        // given
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "image file".getBytes());
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes());
        UpdateUniverseCommand command = new UpdateUniverseCommand(1L, "오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", Category.LIFE, PublicStatus.PRIVATE, List.of("오르트구름", "태양계", "윤하", "별"), thumbnail, thumbMusic);
        Universe universe = MockEntityFactoryService.getUniverse();

        // when
        when(findUniversePort.load(1L)).thenReturn(Optional.ofNullable(universe));
        when(uploadPublicImageUseCase.publicUpload(any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(12L, null, "universe_music.mp3", "test1235.mp3", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS))));
        when(uploadPublicAudioUseCase.publicUpload(any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(101L, null, "universe_thumb.png", "test1234.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS))));

        MessageDto message = sut.update(command);

        // then
        verify(updateUniversePort, times(1)).update(universe);
        assertThat(message.message()).isEqualTo("1번 유니버스가 수정되었습니다.");
        assertThat(universe.getBasicInfo().getTitle()).isEqualTo("오르트구름");
        assertThat(universe.getBasicInfo().getDescription()).isEqualTo("오르트구름은 태양계 최외곽에 위치하고 있습니다.");
        assertThat(universe.getBasicInfo().getCategory()).isEqualTo(Category.LIFE);
        assertThat(universe.getBasicInfo().getPublicStatus()).isEqualTo(PublicStatus.PRIVATE);
        assertThat(universe.getSocialInfo().getHashtags()).contains("오르트구름", "태양계", "윤하", "별");
        assertThat(universe.getThumbnailId()).isEqualTo(12L);
        assertThat(universe.getThumbMusicId()).isEqualTo(101L);
    }
}