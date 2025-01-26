package com.hoo.aoo.admin.application.service.item;

import com.hoo.aoo.admin.adapter.out.persistence.mapper.ItemMapper;
import com.hoo.aoo.admin.application.port.in.item.ItemData;
import com.hoo.aoo.admin.application.port.in.item.UpdateItemCommand;
import com.hoo.aoo.admin.application.port.out.item.FindItemPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.admin.domain.item.ItemType;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import com.hoo.aoo.common.application.service.MockEntityFactoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateItemServiceTest {

    UpdateItemService sut;

    FindItemPort findItemPort;
    ItemMapper itemMapper;

    @BeforeEach
    void init() {
        findItemPort = mock();
        itemMapper = mock();
        sut = new UpdateItemService(findItemPort, itemMapper);
    }

    @Test
    @DisplayName("아이템 업데이트 서비스 테스트")
    void testUpdateItem() throws Exception {
        // given
        Long id = 1L;
        Long notExistId = 1234L;
        UpdateItemCommand command = new UpdateItemCommand(new ItemData(null,"고양이", ItemType.RECTANGLE, null,
                new ItemData.RectangleData(300f, 300f, 20f, 20f, 10f), null));

        Item item = mock();

        // when
        when(findItemPort.load(id)).thenReturn(Optional.ofNullable(item));
        MessageDto messageDto = sut.updateItem(id, command);

        // then
        verify(item, times(1)).update(any(), any());
        assertThatThrownBy(() -> sut.updateItem(notExistId, command)).hasMessage(AdminErrorCode.ITEM_NOT_FOUND.getMessage());
        assertThat(messageDto.message()).isEqualTo("1번 아이템의 정보가 수정되었습니다.");
    }

}