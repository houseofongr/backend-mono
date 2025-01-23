package com.hoo.aoo.admin.application.service.item;

import com.hoo.aoo.admin.application.port.in.item.CreateItemMetadata;
import com.hoo.aoo.admin.application.port.in.item.CreateItemResult;
import com.hoo.aoo.admin.application.port.out.item.SaveItemPort;
import com.hoo.aoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.aoo.common.FixtureRepository;
import com.hoo.aoo.file.application.port.in.UploadFileResult;
import com.hoo.aoo.file.application.port.in.UploadPrivateAudioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateItemServiceTest {

    CreateItemService sut;

    FindRoomPort findRoomPort;
    SaveItemPort saveItemPort;
    UploadPrivateAudioUseCase uploadPrivateAudioUseCase;

    @BeforeEach
    void init() {
        findRoomPort = mock();
        saveItemPort = mock();
        uploadPrivateAudioUseCase = mock();
        sut = new CreateItemService(findRoomPort, saveItemPort, uploadPrivateAudioUseCase);
    }

    @Test
    @DisplayName("아이템 생성 서비스 테스트")
    void testCreateItem() throws Exception {
        // given
        CreateItemMetadata createItemMetadata = FixtureRepository.getCreateItemMetadata();
        Map<String, MultipartFile> map = new HashMap<>();

        map.put("record1", new MockMultipartFile("record1", "강아지.mp3", "audio/mpeg", "walwal".getBytes()));
        map.put("record2", new MockMultipartFile("record2", "설이.mp3", "audio/mpeg", "grrrrgrr".getBytes()));
        map.put("record3", new MockMultipartFile("record3", "화분.mp3", "audio/mpeg", "i'm flower bottle.".getBytes()));

        // when
        when(findRoomPort.load(1L)).thenReturn(Optional.of(FixtureRepository.getRoom()));
        when(uploadPrivateAudioUseCase.privateUpload(any(), any())).thenReturn(new UploadFileResult(List.of(
                new UploadFileResult.FileInfo(1L, 1L, "강아지.mp3", null, null, null),
                new UploadFileResult.FileInfo(2L, 2L, "설이.mp3", null, null, null),
                new UploadFileResult.FileInfo(3L, 3L, "화분.mp3", null, null, null)
        )));
        when(saveItemPort.save(any())).thenReturn(List.of(1L, 2L, 3L));
        CreateItemResult createItemResult = sut.create(1L, 1L, createItemMetadata, map);

        // then
        verify(findRoomPort, times(1)).load(any());
        verify(uploadPrivateAudioUseCase, times(1)).privateUpload(any(), any());
        verify(saveItemPort, times(1)).save(any());

        assertThat(createItemResult).isNotNull();
        assertThat(createItemResult.createdItemIds()).hasSize(3);
    }
}