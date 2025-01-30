package com.hoo.aoo.admin.application.port.out.item;

import com.hoo.aoo.admin.domain.item.Item;

import java.util.List;
import java.util.Optional;

public interface FindItemPort {
    Optional<Item> load(Long id);
    List<Item> loadAllInHomeAndRoom(Long homeId, Long roomId);
    boolean existByRoomId(Long roomId);
}
