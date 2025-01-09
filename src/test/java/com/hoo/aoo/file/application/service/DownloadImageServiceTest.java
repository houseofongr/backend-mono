package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.in.DownloadImageResult;
import com.hoo.aoo.file.application.port.out.database.LoadPublicImageFilePort;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.FileF;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DownloadImageServiceTest {

    DownloadImageService sut;

    LoadPublicImageFilePort loadPublicImageFilePort;

    @BeforeEach
    void init() {
        loadPublicImageFilePort = mock();
        sut = new DownloadImageService(loadPublicImageFilePort);
    }

    @Test
    @DisplayName("이미지 다운로드 테스트")
    void testDownloadImage(@TempDir Path tempDir) throws IOException, FileSizeLimitExceedException {
        // given
        Long fileId = 1L;

        File file = FileF.IMAGE_FILE_1.get(tempDir.toString());
        file.retrieve();

        Files.writeString(file.getJavaFile().toPath(), "test file");

        // when
        when(loadPublicImageFilePort.load(fileId)).thenReturn(Optional.of(file));
        DownloadImageResult result = sut.download(fileId);

        // then
        assertThat(result.disposition()).contains("inline;").contains("test.png");
        assertThat(result.bytes().length).isEqualTo(9);
    }
}