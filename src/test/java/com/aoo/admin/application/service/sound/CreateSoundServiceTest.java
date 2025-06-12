package com.aoo.admin.application.service.sound;

import com.aoo.admin.application.port.in.sound.CreateSoundCommand;
import com.aoo.admin.application.port.in.sound.CreateSoundResult;
import com.aoo.admin.application.port.out.sound.CreateSoundPort;
import com.aoo.admin.application.port.out.sound.SaveSoundPort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.domain.universe.piece.sound.Sound;
import com.aoo.common.domain.Authority;
import com.aoo.file.application.port.in.UploadFileResult;
import com.aoo.file.application.port.in.UploadPublicAudioUseCase;
import com.aoo.file.domain.FileSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CreateSoundServiceTest {

    UploadPublicAudioUseCase uploadPublicAudioUseCase = mock();
    CreateSoundPort createSoundPort = mock();
    SaveSoundPort saveSoundPort = mock();
    CreateSoundService sut = new CreateSoundService(uploadPublicAudioUseCase, createSoundPort, saveSoundPort);

    @Test
    @DisplayName("요청 파라미터 테스트")
    void testRequestParameter() {
        Long goodSpaceId = 1L;
        String goodTitle = "소리";
        String goodDescription = "사운드는 소리입니다.";
        Long nullSpaceId = null;
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);
        String length5000 = "a".repeat(5001);

        assertThatThrownBy(() -> CreateSoundCommand.create(nullSpaceId, goodTitle, goodDescription)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> CreateSoundCommand.create(goodSpaceId, emptyTitle, goodDescription)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> CreateSoundCommand.create(goodSpaceId, blankTitle, goodDescription)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> CreateSoundCommand.create(goodSpaceId, length100, goodDescription)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> CreateSoundCommand.create(goodSpaceId, goodTitle, length5000)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());

        byte[] content = new byte[100 * 1024 * 1024 + 1];
        MockMultipartFile sound = new MockMultipartFile("audio", "audio.png", "audio/mpeg", "audio file".getBytes());
        MockMultipartFile over100MB = new MockMultipartFile("audio", "audio.png", "audio/mpeg", content);
        CreateSoundCommand happyCommand = CreateSoundCommand.create(goodSpaceId, goodTitle, goodDescription);

        assertThatThrownBy(() -> CreateSoundCommand.withAudioFile(happyCommand, over100MB)).hasMessage(AdminErrorCode.EXCEEDED_FILE_SIZE.getMessage());

        CreateSoundCommand happyFullCommand = CreateSoundCommand.withAudioFile(happyCommand, sound);
        assertThat(happyFullCommand.pieceId()).isEqualTo(goodSpaceId);
        assertThat(happyFullCommand.title()).isEqualTo(goodTitle);
        assertThat(happyFullCommand.description()).isEqualTo(goodDescription);
        assertThat(happyFullCommand.audioFile()).isEqualTo(sound);
    }

    @Test
    @DisplayName("사운드 생성 서비스")
    void testCreateSoundService() {
        // given
        CreateSoundCommand happyCommand = CreateSoundCommand.create(1L, "소리", "사운드는 소리입니다.");
        CreateSoundCommand happyFullCommand = CreateSoundCommand.withAudioFile(happyCommand, new MockMultipartFile("audio", "audio.png", "audio/mpeg", "audio file".getBytes()));
        Sound sound = Sound.create(123L, 345L, 1L, "소리", "사운드는 소리입니다.");

        // when
        when(createSoundPort.createSound(anyLong(), any())).thenReturn(sound);
        when(uploadPublicAudioUseCase.publicUpload((MultipartFile) any())).thenReturn(new UploadFileResult.FileInfo(345L, null, "audio.png", "test1234.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PUBLIC_FILE_ACCESS));
        when(saveSoundPort.save(sound)).thenReturn(123L);
        CreateSoundResult result = sut.create(happyFullCommand);

        // then
        assertThat(result.message()).matches("\\[#\\d+]번 사운드가 생성되었습니다.");
        assertThat(result.soundId()).isEqualTo(123L);
        assertThat(result.pieceId()).isEqualTo(1L);
        assertThat(result.audioFileId()).isEqualTo(345L);
        assertThat(result.title()).isEqualTo("소리");
        assertThat(result.description()).isEqualTo("사운드는 소리입니다.");
    }
}