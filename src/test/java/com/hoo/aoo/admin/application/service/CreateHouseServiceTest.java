package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.in.HouseIdResult;
import com.hoo.aoo.admin.application.port.out.database.SaveHousePort;
import com.hoo.aoo.common.domain.Authority;
import com.hoo.aoo.file.application.port.in.UploadImageResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import com.hoo.aoo.file.domain.FileSize;
import com.hoo.aoo.file.domain.exception.FileSizeLimitExceedException;
import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String data = """
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
        Metadata metadata = new Gson().fromJson(data,Metadata.class);
        MockMultipartFile house = new MockMultipartFile("house", "house.png", "image/png", "house file".getBytes());
        MockMultipartFile border = new MockMultipartFile("border", "border.png", "image/png", "border file".getBytes());
        MockMultipartFile room1 = new MockMultipartFile("room1", "livingRoom.png", "image/png", "livingRoom file".getBytes());
        MockMultipartFile room2 = new MockMultipartFile("room2", "kitchen.png", "image/png", "kitchen file".getBytes());

        Map<String, MultipartFile> map = new HashMap<>();

        map.put("house", house);
        map.put("border", border);
        map.put("room1", room1);
        map.put("room2", room2);

        // when
        when(uploadPrivateImageUseCase.privateUpload(any())).thenReturn(new UploadImageResult(List.of(new UploadImageResult.FileInfo(1L, "newfile.png", new FileSize(1234L, 10000L).getUnitSize(), Authority.PRIVATE_FILE_ACCESS))));
        HouseIdResult result = sut.create(metadata, map);

        // then
        verify(uploadPrivateImageUseCase, times(4)).privateUpload(any());
        verify(saveHousePort, times(1)).save(any(), any(), any(), any());

        assertThat(result.houseId()).isNotNull();
    }
}