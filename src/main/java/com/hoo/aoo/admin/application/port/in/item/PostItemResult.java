package com.hoo.aoo.admin.application.port.in.item;

import java.util.List;

public record PostItemResult(
        List<Long> createdItemIds
) {
}
