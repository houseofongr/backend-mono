package com.aoo.admin.application.port.out.item;

import com.aoo.admin.application.port.in.item.ItemData;
import com.aoo.admin.domain.item.Shape;

public interface MappingItemShapePort {
    Shape mapToShape(ItemData itemData);
}
