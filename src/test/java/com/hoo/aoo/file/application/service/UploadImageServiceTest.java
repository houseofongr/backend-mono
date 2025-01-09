package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.adapter.out.filesystem.FileAttribute;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.out.database.SavePublicImageFilePort;
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

    SavePublicImageFilePort savePublicImageFilePort;

    FileAttribute fileAttribute;

    @BeforeEach
    void init(@TempDir Path tempDir) {
        savePublicImageFilePort = mock();
        fileAttribute = new FileAttribute(100 * 1024 * 1024L, tempDir.toString(), tempDir.toString());
        sut = new UploadImageService(savePublicImageFilePort, fileAttribute);
    }

    @Test
    @DisplayName("이미지 업로드 테스트")
    void testUploadImage() {
        // given
        char[] arr = new char[10 * 1024];
        Arrays.fill(arr, 'a');

        MockMultipartFile requestFile = new MockMultipartFile("test.png", "test.png", "image/png", "<<png bin>>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile requestFile2 = new MockMultipartFile("test2.png", "test2.png", "image/png", new String(arr).getBytes(StandardCharsets.UTF_8));
        List<MultipartFile> files = List.of(requestFile, requestFile2);

        // when
        UploadImageResult result = sut.upload(files);

        // then

        // 이미지 엔티티 저장 호출
        verify(savePublicImageFilePort, times(2)).save(any());

        // 결과 확인
        assertThat(result.fileInfos()).hasSize(2);
        assertThat(result.fileInfos())
                .anySatisfy(fileInfo -> {
                    assertThat(fileInfo.id()).isNotNull();
                    assertThat(fileInfo.name()).isEqualTo("test2.png");
                    assertThat(fileInfo.size()).matches("^\\d{1,3}.\\d{1,2}KB$");
                });
    }
}