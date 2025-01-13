package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.adapter.out.filesystem.FileAttribute;
import com.hoo.aoo.file.adapter.out.filesystem.FileSystemAdapter;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.out.database.SaveImageFilePort;
import com.hoo.aoo.file.application.port.out.filesystem.RandomFileNamePort;
import com.hoo.aoo.file.application.port.out.filesystem.WriteFilePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UploadImageServiceTest {

    UploadImageService sut;

    FileAttribute fileAttribute;

    SaveImageFilePort saveImageFilePort;

    WriteFilePort writeFilePort;

    RandomFileNamePort randomFileNamePort;

    MockMultipartFile requestFile;
    MockMultipartFile requestFile2;

    @BeforeEach
    void init(@TempDir Path tempDir) {
        saveImageFilePort = mock();
        fileAttribute = new FileAttribute(100 * 1024 * 1024L, tempDir.toString());
        writeFilePort = mock();
        randomFileNamePort = new FileSystemAdapter();
        sut = new UploadImageService(fileAttribute, saveImageFilePort, writeFilePort, randomFileNamePort);

        char[] arr = new char[10 * 1024];
        Arrays.fill(arr, 'a');

        requestFile = new MockMultipartFile("test.png", "test.png", "image/png", "<<png bin>>".getBytes(StandardCharsets.UTF_8));
        requestFile2 = new MockMultipartFile("test2.png", "test2.png", "image/png", new String(arr).getBytes(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("퍼블릭 이미지 업로드 테스트")
    void testPublicUploadImage() {
        // given
        List<MultipartFile> files = List.of(requestFile, requestFile2);

        // when
        UploadImageResult result = sut.publicUpload(files);

        // then

        // 이미지 엔티티 저장 호출
        verify(saveImageFilePort, times(2)).save(any());

        // 결과 확인
        assertThat(result.fileInfos()).hasSize(2);
        assertThat(result.fileInfos())
                .anySatisfy(fileInfo -> {
                    assertThat(fileInfo.id()).isNotNull();
                    assertThat(fileInfo.name()).contains(".png");
                    assertThat(fileInfo.size()).matches("^\\d{1,3}.\\d{1,2}KB$");
                    assertThat(fileInfo.authority()).isEqualTo(Authority.PUBLIC_FILE_ACCESS);
                });
    }

    @Test
    @DisplayName("프라이빗 이미지 업로드 테스트")
    void testPrivateUploadImage() {
        // given
        List<MultipartFile> files = List.of(requestFile, requestFile2);

        // when
        UploadImageResult result = sut.privateUpload(files);

        // then

        // 이미지 엔티티 저장 호출
        verify(saveImageFilePort, times(2)).save(any());

        // 결과 확인
        assertThat(result.fileInfos()).hasSize(2);
        assertThat(result.fileInfos())
                .anySatisfy(fileInfo -> {
                    assertThat(fileInfo.id()).isNotNull();
                    assertThat(fileInfo.name()).contains(".png");
                    assertThat(fileInfo.size()).matches("^\\d{1,3}.\\d{1,2}KB$");
                    assertThat(fileInfo.authority()).isEqualTo(Authority.PRIVATE_FILE_ACCESS);
                });
    }
}