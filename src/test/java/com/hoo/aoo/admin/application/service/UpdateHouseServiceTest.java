package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.out.LoadHousePort;
import com.hoo.aoo.admin.application.port.out.database.UpdateHousePort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
import com.hoo.aoo.admin.domain.exception.RoomDuplicatedException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.common.FixtureRepository;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import com.hoo.aoo.file.domain.FileSize;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.hoo.aoo.common.FixtureRepository.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UpdateHouseServiceTest {

    UpdateHouseService sut;

    LoadHousePort loadHousePort;
    UpdateHousePort updateHousePort;
    UploadPrivateImageUseCase uploadPrivateImageUseCase;

    @BeforeEach
    void init() {
        loadHousePort = mock();
        updateHousePort = mock();
        uploadPrivateImageUseCase = mock();
        sut = new UpdateHouseService(loadHousePort, updateHousePort, uploadPrivateImageUseCase);
    }

    @Test
    @DisplayName("하우스 업데이트 서비스 테스트")
    void testUpdate() throws Exception {
        // given
        House house = FixtureRepository.getHouseWithRoom(new HouseId("cozy house", "leaf", "this is cozy house"));
        Metadata metadata = getUpdateMetadata();

        Map<String, MultipartFile> fileMap = getFileMap();

        // when
        when(loadHousePort.load(1L)).thenReturn(Optional.of(house));
        when(uploadPrivateImageUseCase.privateUpload((MultipartFile) any())).thenReturn(new UploadImageResult(List.of(new UploadImageResult.FileInfo(1L, "newfile.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PRIVATE_FILE_ACCESS))));
        MessageDto message = sut.update(1L, metadata, fileMap);

        // then
        verify(loadHousePort, times(1)).load(1L);
        verify(uploadPrivateImageUseCase, times(4)).privateUpload((MultipartFile) any());
        verify(updateHousePort, times(1)).update(any(),any());

        assertThat(message.message()).isEqualTo("1번 하우스 수정이 완료되었습니다.");
    }


}