package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.DownloadImageResult;
import com.hoo.aoo.file.application.port.out.database.LoadImageFilePort;
import com.hoo.aoo.file.domain.*;
import com.hoo.aoo.file.domain.exception.FileExtensionMismatchException;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import com.hoo.aoo.file.domain.exception.IllegalFileAuthorityDirException;
import com.hoo.aoo.file.domain.exception.IllegalFileTypeDirException;
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

    LoadImageFilePort loadImageFilePort;

    @BeforeEach
    void init() {
        loadImageFilePort = mock();
        sut = new DownloadImageService(loadImageFilePort);
    }

    @Test
    @DisplayName("이미지 다운로드 테스트")
    void testDownloadImage(@TempDir Path tempDir) throws IOException, FileSizeLimitExceedException, FileExtensionMismatchException, IllegalFileTypeDirException, IllegalFileAuthorityDirException {
        // given
        Long fileId = 1L;
        File file = FileF.IMAGE_FILE_1.get(tempDir.toString());
        java.io.File javaFile = new java.io.File(file.getFileId().getPath());
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();
        Files.writeString(javaFile.toPath(), "test file");

        // when
        when(loadImageFilePort.load(fileId)).thenReturn(Optional.of(file));
        DownloadImageResult result = sut.publicDownload(fileId);

        // then
        assertThat(result.disposition()).contains("inline;").contains(file.getFileId().getFileSystemName());
        assertThat(result.bytes().length).isEqualTo(9);
    }

    @Test
    @DisplayName("프라이빗 이미지 다운로드 테스트")
    void testDownloadPrivate(@TempDir Path tempDir) throws IOException, FileSizeLimitExceedException, FileExtensionMismatchException, IllegalFileTypeDirException, IllegalFileAuthorityDirException {
        // given
        Long fileId = 1L;
        File file = File.create(FileId.create(tempDir.toString(), Authority.PRIVATE_FILE_ACCESS, FileType.IMAGE, "test.png", "test.png"), FileStatus.CREATED, null, new FileSize(1234L, 10000L));
        java.io.File javaFile = new java.io.File(file.getFileId().getPath());
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();
        Files.writeString(javaFile.toPath(), "test file");

        // when
        when(loadImageFilePort.load(fileId)).thenReturn(Optional.of(file));
        DownloadImageResult result = sut.privateDownload(fileId);

        // then
        assertThat(result.disposition()).contains("inline;").contains(file.getFileId().getFileSystemName());
        assertThat(result.bytes().length).isEqualTo(9);
    }
}