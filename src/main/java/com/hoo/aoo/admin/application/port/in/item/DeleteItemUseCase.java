package com.hoo.aoo.admin.application.port.in.item;

import com.hoo.aoo.common.application.port.in.MessageDto;

public interface DeleteItemUseCase {
    MessageDto deleteItem(Long id);
}
