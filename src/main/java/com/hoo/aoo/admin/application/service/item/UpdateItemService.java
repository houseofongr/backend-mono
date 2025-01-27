package com.hoo.aoo.admin.application.service.item;

import com.hoo.aoo.admin.adapter.out.persistence.mapper.ItemMapper;
import com.hoo.aoo.admin.application.port.in.item.UpdateItemCommand;
import com.hoo.aoo.admin.application.port.in.item.UpdateItemUseCase;
import com.hoo.aoo.admin.application.port.out.item.FindItemPort;
import com.hoo.aoo.admin.application.port.out.item.UpdateItemPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateItemService implements UpdateItemUseCase {

    private final FindItemPort findItemPort;
    private final UpdateItemPort updateItemPort;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public MessageDto updateItem(Long id, UpdateItemCommand command) {
        Item item = findItemPort.load(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ITEM_NOT_FOUND));

        item.update(command.updateData().name(), itemMapper.mapToShape(command.updateData()));
        updateItemPort.updateItem(item);

        return new MessageDto(id + "번 아이템의 정보가 수정되었습니다.");
    }
}
