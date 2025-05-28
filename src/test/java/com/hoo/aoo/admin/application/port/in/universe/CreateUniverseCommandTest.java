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

    CreateUniverseCommand command = new CreateUniverseCommand("우주", "유니버스는 우주입니다.", 1L, Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), Map.of());

    @Test
    @DisplayName("잘못된 요청 파라미터")
    void testBadCommand() {
        String nullTitle = null;
        String emptyTitle = "";
        String blankTitle = " ";
        String exceed5000 = "a".repeat(5001);
        List<String> exceedTagCount = List.of("소프트웨어개발", "백엔드개발", "프로그래밍", "자바개발", "웹개발", "데이터베이스설계", "도메인주도설계", "클린코드", "마이크로서비스아키텍처", "헥사고날아키텍처", "+1");
        List<String> exceedTagSize = List.of("a".repeat(501));

        assertThatThrownBy(() -> new CreateUniverseCommand(nullTitle, "유니버스는 우주입니다.", 1L, Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateUniverseCommand(emptyTitle, "유니버스는 우주입니다.", 1L, Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateUniverseCommand(blankTitle, "유니버스는 우주입니다.", 1L, Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateUniverseCommand("우주", exceed5000, 1L, Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, List.of("우주", "행성", "지구", "별"), null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateUniverseCommand("우주", "유니버스는 우주입니다.", 1L, Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, exceedTagSize, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
        assertThatThrownBy(() -> new CreateUniverseCommand("우주", "유니버스는 우주입니다.", 1L, Category.GOVERNMENT_AND_PUBLIC_INSTITUTION, PublicStatus.PUBLIC, exceedTagCount, null)).hasMessage(AdminErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("썸네일, 썸뮤직, 내부이미지 파일 누락")
    void testFileOmissionOrDuplicateName() {
        // given
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "universe_inner_image.png", "image/png", "image file".getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "image file".getBytes());
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes());

        // when
        Map<String, MultipartFile> empty = Map.of();
        Map<String, MultipartFile> noThumbnail = Map.of("thumbMusic", thumbMusic, "innerImage", innerImage);
        Map<String, MultipartFile> noThumbMusic = Map.of("thumbnail", thumbnail,  "innerImage", innerImage);
        Map<String, MultipartFile> noInnerImage = Map.of("thumbnail", thumbnail, "thumbMusic", thumbMusic);

        // then
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, empty)).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, noThumbnail)).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, noThumbMusic)).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, noInnerImage)).hasMessage(AdminErrorCode.UNIVERSE_FILE_REQUIRED.getMessage());
    }

    @Test
    @DisplayName("썸네일, 썸뮤직, 내부이미지 용량 초과")
    void testFileSizeExceeded() {
        // given
        byte[] content = new byte[2 * 1024 * 1024 + 1];
        byte[] content2 = new byte[5 * 1024 * 1024 + 1];

        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", "image file".getBytes());
        MockMultipartFile thumbnailExceed = new MockMultipartFile("thumbnail", "universe_thumb.png", "image/png", content);
        MockMultipartFile thumbMusic = new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", "music file".getBytes());
        MockMultipartFile thumbMusicExceed = new MockMultipartFile("thumbMusic", "universe_music.mp3", "audio/mpeg", content);
        MockMultipartFile innerImage = new MockMultipartFile("innerImage", "universe_inner_image.png", "image/png", content);
        MockMultipartFile innerImageExceed = new MockMultipartFile("innerImage", "universe_inner_image.png", "image/png", content2);

        // when
        Map<String, MultipartFile> exceedThumbnailSize = Map.of("thumbnail", thumbnailExceed, "thumbMusic", thumbMusic, "innerImage", innerImage);
        Map<String, MultipartFile> exceedThumbMusicSize = Map.of("thumbnail", thumbnail, "thumbMusic", thumbMusicExceed, "innerImage", innerImage);
        Map<String, MultipartFile> exceedInnerImage = Map.of("thumbnail", thumbnail, "thumbMusic", thumbMusic, "innerImage", innerImageExceed);

        // then
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, exceedThumbnailSize)).hasMessage(AdminErrorCode.EXCEEDED_UNIVERSE_FILE_SIZE.getMessage());
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, exceedThumbMusicSize)).hasMessage(AdminErrorCode.EXCEEDED_UNIVERSE_FILE_SIZE.getMessage());
        assertThatThrownBy(() -> CreateUniverseCommand.from(command, exceedInnerImage)).hasMessage(AdminErrorCode.EXCEEDED_UNIVERSE_FILE_SIZE.getMessage());
    }

}