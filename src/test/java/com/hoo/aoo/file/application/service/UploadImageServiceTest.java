package com.hoo.aoo.file.application.service;

import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.out.database.SaveFilePersistencePort;
import com.hoo.aoo.file.application.port.out.filesystem.StoreImageFileSystemPort;
import com.hoo.aoo.file.domain.File;
import com.hoo.aoo.file.domain.FileF;
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

    SaveFilePersistencePort saveFilePersistencePort;

    StoreImageFileSystemPort storeImageFilesystemPort;

    @BeforeEach
    void init() {
        saveFilePersistencePort = mock();
        storeImageFilesystemPort = mock();
        sut = new UploadImageService(saveFilePersistencePort, storeImageFilesystemPort);
    }

    @Test
    @DisplayName("이미지 업로드 테스트")
    void testUploadImage() {
        // given
        char[] arr = new char[10 * 1024];
        Arrays.fill(arr, 'a');

        MockMultipartFile requestFile = new MockMultipartFile("test.png", "<<png bin>>".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile requestFile2 = new MockMultipartFile("test2.png", new String(arr).getBytes(StandardCharsets.UTF_8));
        List<MultipartFile> files = List.of(requestFile, requestFile2);

        File file = FileF.IMAGE_FILE_1.get("/tmp/");

        // when
        when(storeImageFilesystemPort.storePublicFile(any())).thenReturn(file);
        UploadImageResult result = sut.upload(files);

        // then

        // 이미지 엔티티 저장 호출
        verify(saveFilePersistencePort, times(2)).savePublicFile(any());

        // 이미지 파일 저장 호출
        verify(storeImageFilesystemPort, times(2)).storePublicFile(any());

        // 결과 확인
        assertThat(result.fileInfos()).hasSize(2);
        assertThat(result.fileInfos())
                .anySatisfy(fileInfo -> {
                    assertThat(fileInfo.id()).isNotNull();
                    assertThat(fileInfo.name()).isEqualTo("test.png");
                    assertThat(fileInfo.size()).matches("^\\d{1,3}.\\d{1,2}KB$");
                });
    }
}