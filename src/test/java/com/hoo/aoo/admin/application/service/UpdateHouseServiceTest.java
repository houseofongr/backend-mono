package com.hoo.aoo.admin.application.service;

import com.hoo.aoo.admin.application.port.out.LoadHousePort;
import com.hoo.aoo.admin.application.port.out.database.UpdateHousePort;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import com.hoo.aoo.file.application.port.in.UploadPrivateImageUseCase;
import com.nimbusds.jose.shaded.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

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
    void testUpdate() {
        // given
        Metadata metadata = getMetadata();
        Map<String, MultipartFile> fileMap = getFileMap();

        // when
        MessageDto message = sut.update(1L, metadata, fileMap);

        // then
        verify(loadHousePort, times(1)).load(1L);
        verify(updateHousePort, times(1)).update(any(),any(),any());
        verify(uploadPrivateImageUseCase, times(4)).privateUpload(any());

        assertThat(message.message()).isEqualTo("1번 하우스 수정이 완료되었습니다.");
    }

    private Map<String, MultipartFile> getFileMap() {
        MockMultipartFile house = new MockMultipartFile("house", "house.png", "image/png", "house file".getBytes());
        MockMultipartFile border = new MockMultipartFile("border", "border.png", "image/png", "border file".getBytes());
        MockMultipartFile room1 = new MockMultipartFile("room1", "livingRoom.png", "image/png", "livingRoom file".getBytes());
        MockMultipartFile room2 = new MockMultipartFile("room2", "kitchen.png", "image/png", "kitchen file".getBytes());

        Map<String, MultipartFile> map = new HashMap<>();

        map.put("house", house);
        map.put("border", border);
        map.put("room1", room1);
        map.put("room2", room2);

        return map;
    }

    private Metadata getMetadata() {
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
        return new Gson().fromJson(data, Metadata.class);
    }

}