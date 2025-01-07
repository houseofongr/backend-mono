package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.out.database.SaveFileEntityPort;
import com.hoo.aoo.file.application.port.out.filesystem.SaveFilePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UploadImageServiceTest {

    UploadImageService sut;

    SaveFileEntityPort saveFileEntityPort;

    SaveFilePort saveFilePort;

    @BeforeEach
    void init() {
        saveFileEntityPort = mock();
        saveFilePort = mock();
        sut = new UploadImageService(saveFileEntityPort, saveFilePort);
    }

    @Test
    @DisplayName("이미지 업로드 테스트")
    void testUploadImage() {
        // given
        char[] arr = new char[10 * 1024];
        Arrays.fill(arr, 'a');

        MockMultipartFile file = new MockMultipartFile("test.jpg", "<<png bin>>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile file2 = new MockMultipartFile("test2.jpg", new String(arr).getBytes(StandardCharsets.UTF_8));
        List<MultipartFile> files = List.of(file, file2);

        // when
        UploadImageResult result = sut.upload(files);

        // then

        // 이미지 엔티티 저장 호출
        verify(saveFileEntityPort, times(2)).save(any());

        // 이미지 파일 저장 호출
        verify(saveFilePort, times(2)).save(any());

        // 결과 확인
        assertThat(result.fileInfos()).hasSize(2);
        assertThat(result.fileInfos())
                .anySatisfy(fileInfo -> {
                    assertThat(fileInfo.id()).isNotNull();
                    assertThat(fileInfo.name()).isEqualTo("test.jpg");
                    assertThat(fileInfo.size()).matches("^\\d{1,3}Byte$");
                }).anySatisfy(fileInfo -> {
                    assertThat(fileInfo.id()).isNotNull();
                    assertThat(fileInfo.name()).isEqualTo("test2.jpg");
                    assertThat(fileInfo.size()).matches("^\\d{1,3}.\\d{2}KB$");
                });
    }
}