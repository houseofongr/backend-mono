package com.hoo.aoo.admin.domain.item;

import com.hoo.aoo.admin.domain.house.room.RoomId;
import lombok.Getter;

@Getter
public class ItemId {
    private final Long id;

    public ItemId(Long id) {
        this.id = id;
    }
}
