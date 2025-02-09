package com.hoo.aoo.file.application.service;

import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.adapter.out.filesystem.FileSystemAdapter;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.out.database.SaveImageFilePort;
import com.hoo.aoo.file.application.port.out.filesystem.RandomFileNamePort;
import com.hoo.aoo.file.application.port.out.filesystem.WriteFilePort;
import com.hoo.aoo.file.domain.FileType;
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

class UploadServiceTest {

    UploadService sut;

    FileProperties fileProperties;

    SaveImageFilePort saveImageFilePort;

    WriteFilePort writeFilePort;

    RandomFileNamePort randomFileNamePort;

    MockMultipartFile imageFile;
    MockMultipartFile imageFile2;
    MockMultipartFile audioFile;

    @BeforeEach
    void init(@TempDir Path tempDir) {
        saveImageFilePort = mock();
        fileProperties = new FileProperties(100 * 1024 * 1024L, tempDir.toString());
        writeFilePort = mock();
        randomFileNamePort = new FileSystemAdapter();
        sut = new UploadService(fileProperties, saveImageFilePort, writeFilePort, randomFileNamePort);

        char[] arr = new char[10 * 1024];
        Arrays.fill(arr, 'a');

        imageFile = new MockMultipartFile("test.png", "test.png", "image/png", "<<png bin>>".getBytes(StandardCharsets.UTF_8));
        imageFile2 = new MockMultipartFile("test2.png", "test2.png", "image/png", new String(arr).getBytes(StandardCharsets.UTF_8));
        audioFile = new MockMultipartFile("test.mp3", "test.mp3", "audio/mpeg", new String(arr).getBytes(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("퍼블릭 이미지 업로드 테스트")
    void testPublicUploadImage() {
        // given
        List<MultipartFile> files = List.of(imageFile, imageFile2);
        BasicFileIdCreateStrategy fileIdCreateStrategy = new BasicFileIdCreateStrategy("/tmp", Authority.PUBLIC_FILE_ACCESS, FileType.IMAGE);

        // when
        UploadFileResult result = sut.upload(files, fileIdCreateStrategy);

        // then

        // 이미지 엔티티 저장 호출
        verify(saveImageFilePort, times(2)).save(any());

        // 결과 확인
        assertThat(result.fileInfos()).hasSize(2);
        assertThat(result.fileInfos())
                .anySatisfy(fileInfo -> {
                    assertThat(fileInfo.id()).isNotNull();
                    assertThat(fileInfo.realName()).isEqualTo("test2.png");
                    assertThat(fileInfo.fileSystemName()).contains(".png");
                    assertThat(fileInfo.size()).matches("^\\d{1,3}.\\d{1,2}KB$");
                    assertThat(fileInfo.authority()).isEqualTo(Authority.PUBLIC_FILE_ACCESS);
                });
    }

    @Test
    @DisplayName("프라이빗 이미지 업로드 테스트")
    void testPrivateUploadImage() {
        // given
        List<MultipartFile> files = List.of(imageFile, imageFile2);
        BasicFileIdCreateStrategy fileIdCreateStrategy = new BasicFileIdCreateStrategy("/tmp", Authority.PRIVATE_FILE_ACCESS, FileType.IMAGE);

        // when
        UploadFileResult result = sut.upload(files, fileIdCreateStrategy);

        // then

        // 이미지 엔티티 저장 호출
        verify(saveImageFilePort, times(2)).save(any());

        // 결과 확인
        assertThat(result.fileInfos()).hasSize(2);
        assertThat(result.fileInfos())
                .anySatisfy(fileInfo -> {
                    assertThat(fileInfo.id()).isNotNull();
                    assertThat(fileInfo.realName()).isEqualTo("test2.png");
                    assertThat(fileInfo.fileSystemName()).contains(".png");
                    assertThat(fileInfo.size()).matches("^\\d{1,3}.\\d{1,2}KB$");
                    assertThat(fileInfo.authority()).isEqualTo(Authority.PRIVATE_FILE_ACCESS);
                });
    }

    @Test
    @DisplayName("프라이빗 오디오 업로드 테스트")
    void testPrivateUploadAudio() {
        // given
        List<MultipartFile> files = List.of(audioFile);
        BasicFileIdCreateStrategy fileIdCreateStrategy = new BasicFileIdCreateStrategy("/tmp", Authority.PRIVATE_FILE_ACCESS, FileType.AUDIO);

        // when
        UploadFileResult result = sut.upload(files, 123L, fileIdCreateStrategy);

        // then
        assertThat(result.fileInfos()).hasSize(1);
        assertThat(result.fileInfos())
                .anySatisfy(fileInfo -> {
                    assertThat(fileInfo.id()).isNotNull();
                    assertThat(fileInfo.ownerId()).isEqualTo(123L);
                    assertThat(fileInfo.realName()).isEqualTo("test.mp3");
                    assertThat(fileInfo.fileSystemName()).contains(".mp3");
                    assertThat(fileInfo.size()).matches("^\\d{1,3}.\\d{1,2}KB$");
                    assertThat(fileInfo.authority()).isEqualTo(Authority.PRIVATE_FILE_ACCESS);
                });
    }
}