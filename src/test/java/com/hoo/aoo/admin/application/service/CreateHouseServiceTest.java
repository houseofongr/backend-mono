package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.house.HouseIdResult;
import com.hoo.aoo.admin.application.port.out.SaveHousePort;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import com.hoo.aoo.file.domain.FileSize;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.hoo.aoo.common.FixtureRepository.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateHouseServiceTest {

    CreateHouseService sut;

    SaveHousePort saveHousePort;
    UploadPrivateImageUseCase uploadPrivateImageUseCase;

    @BeforeEach
    void init() {
        saveHousePort = mock();
        uploadPrivateImageUseCase = mock();
        sut = new CreateHouseService(saveHousePort, uploadPrivateImageUseCase);
    }

    @Test
    @DisplayName("하우스 생성 테스트")
    void testCreateHouse() throws FileSizeLimitExceedException {
        // given
        Metadata metadata = getMetadata();
        Map<String, MultipartFile> map = getFileMap();

        // when
        when(uploadPrivateImageUseCase.privateUpload((MultipartFile) any())).thenReturn(new UploadImageResult(List.of(new UploadImageResult.FileInfo(1L, "newfile.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PRIVATE_FILE_ACCESS))));
        HouseIdResult result = sut.create(metadata, map);

        // then
        verify(uploadPrivateImageUseCase, times(4)).privateUpload((MultipartFile) any());
        verify(saveHousePort, times(1)).save(any(), any(), any());

        assertThat(result.houseId()).isNotNull();
    }

}