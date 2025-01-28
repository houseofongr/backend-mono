package com.hoo.aoo.admin.application.port.out.item;

import com.hoo.aoo.admin.application.port.in.item.ItemData;
import com.hoo.aoo.admin.domain.item.Shape;

public interface MappingItemShapePort {
    Shape mapToShape(ItemData itemData);
}
