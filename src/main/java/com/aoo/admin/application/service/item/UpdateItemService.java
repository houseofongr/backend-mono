package com.aoo.admin.application.service.item;

import com.aoo.admin.application.port.in.item.UpdateItemCommand;
import com.aoo.admin.application.port.in.item.UpdateItemUseCase;
import com.aoo.admin.application.port.out.item.FindItemPort;
import com.aoo.admin.application.port.out.item.MappingItemShapePort;
import com.aoo.admin.application.port.out.item.UpdateItemPort;
import com.aoo.admin.application.service.AdminErrorCode;
import com.aoo.admin.application.service.AdminException;
import com.aoo.admin.domain.item.Item;
import com.aoo.common.application.port.in.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateItemService implements UpdateItemUseCase {

    private final FindItemPort findItemPort;
    private final UpdateItemPort updateItemPort;
    private final MappingItemShapePort mappingItemPort;

    @Override
    @Transactional
    public MessageDto updateItem(Long id, UpdateItemCommand command) {
        Item item = findItemPort.loadItem(id)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ITEM_NOT_FOUND));

        item.update(command.updateData().name(), mappingItemPort.mapToShape(command.updateData()));
        updateItemPort.updateItem(item);

        return new MessageDto(id + "번 아이템의 정보가 수정되었습니다.");
    }
}
