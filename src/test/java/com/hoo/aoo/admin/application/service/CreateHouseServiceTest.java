package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.adapter.out.persistence.HousePersistenceAdapter;
import com.hoo.aoo.admin.application.port.in.CreateHouseResult;
import com.hoo.aoo.admin.application.port.out.database.SaveHousePort;
import com.hoo.aoo.admin.application.port.out.database.SaveRoomPort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import com.hoo.aoo.file.domain.FileSize;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateHouseServiceTest {

    CreateHouseService sut;

    SaveRoomPort saveRoomPort;
    SaveHousePort saveHousePort;
    UploadPrivateImageUseCase uploadPrivateImageUseCase;

    @BeforeEach
    void init() {
        saveRoomPort = mock();
        saveHousePort = mock();
        uploadPrivateImageUseCase = mock();
        sut = new CreateHouseService(saveRoomPort, saveHousePort, uploadPrivateImageUseCase);
    }

    @Test
    @DisplayName("하우스 생성 테스트")
    void testCreateHouse() throws AxisLimitExceededException, AreaLimitExceededException, FileSizeLimitExceedException {
        // given
        String metadate = """
                {
                  "house": {
                    "title": "cozy house",
                    "author": "leaf",
                    "description": "this is cozy house.",
                    "width": 5000,
                    "height": 5000,
                    "houseFormName": "house",
                    "borderFormName": "border"
                  },
                  "rooms": [
                    {
                      "formName": "room1",
                      "name": "거실",
                      "x": 123,
                      "y": 456,
                      "z": 1,
                      "width": 100,
                      "height": 200
                    },
                    {
                      "formName": "room2",
                      "name": "주방",
                      "x": 234,
                      "y": 345,
                      "z": 2,
                      "width": 200,
                      "height": 200
                    }
                  ]
                }
                """;
        MockMultipartFile house = new MockMultipartFile("house", "house.png", "image/png", "house file".getBytes());
        MockMultipartFile border = new MockMultipartFile("border", "border.png", "image/png", "border file".getBytes());
        MockMultipartFile room1 = new MockMultipartFile("room1", "livingRoom.png", "image/png", "livingRoom file".getBytes());
        MockMultipartFile room2 = new MockMultipartFile("room2", "kitchen.png", "image/png", "kitchen file".getBytes());

        Map<String, Object> map = new HashMap<>();

        map.put("metadata", metadate);
        map.put("house", house);
        map.put("border", border);
        map.put("room1", room1);
        map.put("room2", room2);

        // when
        when(uploadPrivateImageUseCase.privateUpload(any())).thenReturn(new UploadImageResult(List.of(new UploadImageResult.FileInfo(1L, "newfile.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PRIVATE_FILE_ACCESS))));
        CreateHouseResult result = sut.create(map);

        // then
        verify(uploadPrivateImageUseCase, times(4)).privateUpload(any());
        verify(saveRoomPort, times(2)).save(any());
        verify(saveHousePort, times(1)).save(any());

        assertThat(result.house().id()).isNotNull();
        assertThat(result.house().imageFileId()).isNotNull();
        assertThat(result.house().title()).isEqualTo("cozy house");
        assertThat(result.house().author()).isEqualTo("leaf");
        assertThat(result.house().roomCnt()).isEqualTo(2);

        assertThat(result.rooms()).hasSize(2);
        assertThat(result.rooms()).anySatisfy((room -> {
            assertThat(room.id()).isNotNull();
            assertThat(room.imageFileId()).isNotNull();
            assertThat(room.name()).isEqualTo("거실");
        }));
    }
}