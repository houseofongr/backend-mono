package com.hoo.aoo.admin.application.port.in.universe;

import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.domain.universe.Category;
import com.hoo.aoo.admin.domain.universe.PublicStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateUniverseCommandTest {

    @Test
    @DisplayName("잘못된 요청 파라미터")
    void testBadCommand() {
        String nullTitle = null;
        String emptyTitle = "";
        String blankTitle = " ";
        String exceed5000 = "a".repeat(5001);
        List<String> exceedTagCount = List.of("소프트웨어개발", "백엔드개발", "프로그래밍", "자바개발", "웹개발", "데이터베이스설계", "도메인주도설계", "클린코드", "마이크로서비스아키텍처", "헥사고날아키텍처", "+1");
        List<String> exceedTagSize = List.of("a".repeat(501));

        assertThatThrownBy(() -> new CreateUniverseCommand(nullTitle, "유니버스는 우주입니다.", Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateUniverseCommand(emptyTitle, "유니버스는 우주입니다.", Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateUniverseCommand(blankTitle, "유니버스는 우주입니다.", Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateUniverseCommand("우주", exceed5000, Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateUniverseCommand("우주", "유니버스는 우주입니다.", Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, exceedTagSize, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateUniverseCommand("우주", "유니버스는 우주입니다.", Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, exceedTagCount, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("썸네일, 썸뮤직 파일 누락")
    void testFileOmissionOrDuplicateName() {
        // given
        CreateUniverseCommand command = new CreateUniverseCommand("우주", "유니버스는 우주입니다.", Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), Map.of());
        byte[] content = new byte[2 * 1024 * 1024 + 1];

        // when
        Map<String, MultipartFile> empty = Map.of();
        Map<String, MultipartFile> noThumbnail = Map.of("thumbMusic", new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes()));
        Map<String, MultipartFile> noThumbMusic = Map.of("thumbnail", new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "universe file".getBytes()));
        Map<String, MultipartFile> exceedThumbnailSize = Map.of("thumbnail", new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", content), "thumbMusic", new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes()));
        Map<String, MultipartFile> exceedThumbMusicSize = Map.of("thumbnail", new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "universe file".getBytes()), "thumbMusic", new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", content));

        // then
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, empty)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, noThumbnail)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, noThumbMusic)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, exceedThumbnailSize)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, exceedThumbMusicSize)).hasMessage(AdminErrorCode.ILLEGAL_UNIVERSE_CREATE_FILE.getMessage());
    }

}