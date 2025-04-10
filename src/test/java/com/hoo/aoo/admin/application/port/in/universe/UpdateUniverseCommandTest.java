package com.hoo.aoo.admin.application.port.in.universe;

import com.hoo.aoo.admin.application.port.in.user.UpdateUserInfoCommand;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.domain.universe.PublicStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.hoo.aoo.admin.domain.universe.Category.LIFE;
import static org.assertj.core.api.AssertionsForClassTypes.*;

class UpdateUniverseCommandTest {

    @Test
    @DisplayName("요청 ID 필수조건 확인")
    void testIdCondition() {
        assertThatThrownBy(() -> new UpdateUniverseCommand(null, null, null, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateUniverseCommand(0L, null, null, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("제목(100자/NB) 조건 확인")
    void testTitleCondition() {
        String emptyTitle = "";
        String blankTitle = " ";
        String length100 = "a".repeat(101);

        assertThatThrownBy(() -> new UpdateUniverseCommand(1L, emptyTitle, null, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateUniverseCommand(1L, blankTitle, null, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateUniverseCommand(1L, length100, null, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("내용(5000자) 조건 확인")
    void testDescriptionCondition() {
        String length5000 = "a".repeat(5001);

        assertThatThrownBy(() -> new UpdateUniverseCommand(1L, "우주", length5000, null, null, null, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("태그개수 확인하기(10개, 500자)")
    void testTagCondition() {
        List<String> tag11 = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        List<String> tagLength500 = List.of("a".repeat(501));

        assertThatThrownBy(() -> new UpdateUniverseCommand(1L, null, null, null, null, tag11, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new UpdateUniverseCommand(1L, null, null, null, null, tagLength500, null, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("파일용량 확인하기(2MB)")
    void testFileSize() {
        // given
        UpdateUniverseCommand command = new UpdateUniverseCommand(1L, null, null, null, null, null, null, null);
        byte[] content = new byte[2 * 1024 * 1024 + 1];

        // when
        MockMultipartFile exceedThumbnailSize = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", content);
        MockMultipartFile exceedThumbMusicSize = new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", content);

        // then
        assertThatThrownBy(() -> UpdateUniverseCommand.from(command, exceedThumbnailSize, null)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
        assertThatThrownBy(() -> UpdateUniverseCommand.from(command, null, exceedThumbMusicSize)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
    }

    @Test
    @DisplayName("정상 요청")
    void happyCase() {
        UpdateUniverseCommand command = new UpdateUniverseCommand(1L, "오르트구름", "오르트구름은 태양계 최외곽에 위치하고 있습니다.", LIFE, PublicStatus.PRIVATE, List.of("오르트구름", "태양계", "윤하", "별"), null, null);
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "image file".getBytes());
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes());
        UpdateUniverseCommand fullCommand = UpdateUniverseCommand.from(command, thumbnail, thumbMusic);

        assertThat(fullCommand).isNotNull();
    }
}