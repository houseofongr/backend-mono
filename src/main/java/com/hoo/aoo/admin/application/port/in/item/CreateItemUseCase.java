package com.hoo.aoo.admin.application.port.in.item;

public interface CreateItemUseCase {
    CreateItemResult create(Long userId, Long homeId, Long roomId, CreateItemCommand command);
}
