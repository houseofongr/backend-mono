package com.hoo.aoo.file.domain;

import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    @Test
    @DisplayName("이미지파일 생성 테스트")
    void testCreateImageFile() throws FileSizeLimitExceedException, FileNotFoundException {
        // given
        java.io.File javaFile = new java.io.File("/tmp/test.png");

        // when
        File file = File.createImageFile(new FileId("/tmp/", "test.png"), 10000L);

        // then
        assertThat(file.getJavaFile()).isNull();
        assertThat(file.getFileId().getPath()).isEqualTo(javaFile.getAbsolutePath());

        file.retrieve();
        assertThat(file.getJavaFile()).isEqualTo(javaFile);
    }

    @Test
    @DisplayName("이미지 확장자 테스트")
    void testImageExtension() {
        File.verifyExtension(FileType.IMAGE, "test.png");
    }
}