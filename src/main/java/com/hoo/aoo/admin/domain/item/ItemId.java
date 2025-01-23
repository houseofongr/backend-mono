package com.hoo.aoo.admin.domain.item;

import com.hoo.aoo.admin.domain.house.room.RoomId;
import lombok.Getter;

@Getter
public class ItemId {
    private final Long roomId;
    private final String name;

    public ItemId(Long roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }
}
