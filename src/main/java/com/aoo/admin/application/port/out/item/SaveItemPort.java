package com.aoo.admin.application.port.out.item;

import com.aoo.admin.domain.item.Item;

import java.util.List;

public interface SaveItemPort {
    List<Long> save(Long homeId, Long roomId, List<Item> items);
}
