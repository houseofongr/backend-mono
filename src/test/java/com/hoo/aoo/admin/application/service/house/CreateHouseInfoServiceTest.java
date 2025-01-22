package com.hoo.aoo.admin.application.service.house;

import com.hoo.aoo.admin.application.port.in.house.CreateHouseResult;
import com.hoo.aoo.admin.application.port.out.house.SaveHousePort;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
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

class CreateHouseInfoServiceTest {

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
        CreateHouseMetadata metadata = getMetadata();
        Map<String, MultipartFile> map = getFileMap();

        // when
        when(uploadPrivateImageUseCase.privateUpload((List<MultipartFile>) any())).thenReturn(new UploadFileResult(List.of(new UploadFileResult.FileInfo(1L, null,"newfile.png","newfile1241325.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PRIVATE_FILE_ACCESS))));
        CreateHouseResult result = sut.create(metadata, map);

        // then
        verify(uploadPrivateImageUseCase, times(1)).privateUpload((List<MultipartFile>) any());
        verify(saveHousePort, times(1)).save(any(), any(), any());

        assertThat(result.houseId()).isNotNull();
    }

}