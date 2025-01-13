package com.hoo.aoo.file.adapter.out.filesystem;

import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.FileF;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileSystemAdapterTest {

    FileSystemAdapter sut;

    @TempDir
    Path tempDir;

    @BeforeEach
    void init() {
        sut = new FileSystemAdapter();
    }

    @Test
    @DisplayName("파일 시스템 저장 테스트")
    void testSave() throws IOException {
        // given
        File file = FileF.IMAGE_FILE_1.get(tempDir.toString());
        MockMultipartFile requestFile = new MockMultipartFile("test.png", "test.png", "image/png", "<<png bin>>".getBytes(StandardCharsets.UTF_8));

        // when
        sut.write(file, requestFile);

        // then
        java.io.File writtenFile = new java.io.File(file.getFileId().getPath());
        assertThat(writtenFile).isReadable();
        assertThat(Files.readAllBytes(writtenFile.toPath())).isEqualTo(requestFile.getBytes());
    }
}