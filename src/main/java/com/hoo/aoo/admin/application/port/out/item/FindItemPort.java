package com.hoo.aoo.admin.application.port.out.item;

import com.hoo.aoo.admin.domain.item.Item;

import java.util.List;
import java.util.Optional;

public interface FindItemPort {
    Optional<Item> loadItem(Long id);
    List<Item> loadAllItemsInHome(Long homeId);
    List<Item> loadAllItemsInHomeAndRoom(Long homeId, Long roomId);
    boolean existItemByRoomId(Long roomId);
}
