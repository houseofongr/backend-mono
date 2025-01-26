package com.hoo.aoo.admin.application.port.in.item;

import com.hoo.aoo.common.adapter.in.web.MessageDto;

public interface UpdateItemUseCase {
    MessageDto updateItem(Long id, UpdateItemCommand command);
}
