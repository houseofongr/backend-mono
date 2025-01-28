package com.hoo.aoo.admin.application.port.in.item;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface UpdateItemUseCase {
    MessageDto updateItem(Long id, UpdateItemCommand command);
}
