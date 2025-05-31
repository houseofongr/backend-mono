package com.aoo.admin.application.port.in.item;

import java.util.List;

public record CreateAndUpdateItemCommand(
        List<ItemData> createItems,
        List<ItemData> updateItems
) {
}
