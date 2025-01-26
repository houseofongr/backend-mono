package com.hoo.aoo.admin.application.port.in.item;

import com.hoo.aoo.admin.domain.item.ItemType;

public record UpdateItemCommand(
        ItemData updateData
) {
}
