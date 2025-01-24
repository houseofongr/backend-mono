package com.hoo.aoo.admin.application.service.item;

import com.hoo.aoo.admin.application.port.in.item.CreateItemCommand;
import com.hoo.aoo.admin.application.port.in.item.CreateItemResult;
import com.hoo.aoo.admin.application.port.in.item.ItemData;
import com.hoo.aoo.admin.application.port.out.home.FindHomePort;
import com.hoo.aoo.admin.application.port.out.item.CreateItemPort;
import com.hoo.aoo.admin.application.port.out.item.SaveItemPort;
import com.hoo.aoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.aoo.admin.application.port.out.user.FindUserPort;
import com.hoo.aoo.admin.domain.item.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreateItemServiceTest {

    CreateItemService sut;

    FindUserPort findUserPort;
    FindHomePort findHomePort;
    FindRoomPort findRoomPort;
    SaveItemPort saveItemPort;
    CreateItemPort createItemPort;

    @BeforeEach
    void init() {
        findUserPort = mock();
        findHomePort = mock();
        findRoomPort = mock();
        saveItemPort = mock();
        createItemPort = mock();
        sut = new CreateItemService(findUserPort, findHomePort, findRoomPort, saveItemPort, createItemPort);
    }

    @Test
    @DisplayName("아이템 생성 서비스 테스트")
    void testCreateItem() throws Exception {
        // given
        CreateItemCommand command = new CreateItemCommand(
                List.of(
                        new ItemData(1L,"강아지", ItemType.CIRCLE, new ItemData.CircleData(200f, 200f, 10.5f), null, null),
                        new ItemData(2L,"설이", ItemType.RECTANGLE, null, new ItemData.RectangleData(100f, 100f, 10f, 10f, 5f), null),
                        new ItemData(3L,"화분", ItemType.ELLIPSE, null, null, new ItemData.EllipseData(500f, 500f, 15f, 15f, 90f))
                )
        );

        // when
        when(findUserPort.exist(1L)).thenReturn(true);
        when(findHomePort.exist(1L)).thenReturn(true);
        when(findRoomPort.exist(1L)).thenReturn(true);
        when(saveItemPort.save(any(), any(), any(), any())).thenReturn(List.of(1L, 2L, 3L));
        CreateItemResult createItemResult = sut.create(1L, 1L, 1L, command);

        // then
        verify(saveItemPort, times(1)).save(any(), any(), any(), any());

        assertThat(createItemResult).isNotNull();
        assertThat(createItemResult.createdItemIds()).hasSize(3);
    }
}