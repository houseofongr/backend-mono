package com.hoo.aoo.file.adapter.out.filesystem;

import com.hoo.aoo.file.domain.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

class FileSystemAdapterTest {

    @TempDir
    java.io.File tempDir;

    FileSystemAdapter sut;

    @BeforeEach
    void init() {
        FileAttribute fileAttribute = new FileAttribute(
                100 * 1024 * 1024L,
                tempDir.getPath(),
                null
        );

        sut = new FileSystemAdapter(fileAttribute);
    }

    @Test
    @DisplayName("파일 시스템 저장 테스트")
    void testStoreFile() {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.png",
                "image/png",
                "Hello, World!".getBytes()
        );

        // when
        File result = sut.storePublicFile(mockMultipartFile);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getFileId().getFileName()).isEqualTo("test.png");
        assertThat(result.getFileId().getDirectory()).isEqualTo(tempDir.getPath());
        assertThat(result.getJavaFile()).binaryContent().isEqualTo("Hello, World!".getBytes());
    }
}