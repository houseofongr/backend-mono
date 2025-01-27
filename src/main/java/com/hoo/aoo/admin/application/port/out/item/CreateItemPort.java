package com.hoo.aoo.admin.application.port.out.item;

import com.hoo.aoo.admin.domain.item.Item;
import com.hoo.aoo.admin.domain.item.Shape;

public interface CreateItemPort {
    Item createItem(Long userId, Long roomId, String name, Shape shape);
}
