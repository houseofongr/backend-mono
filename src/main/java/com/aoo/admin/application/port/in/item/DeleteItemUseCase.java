package com.aoo.admin.application.port.in.item;

import com.aoo.common.application.port.in.MessageDto;

public interface DeleteItemUseCase {
    MessageDto deleteItem(Long id);
}
