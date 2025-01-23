package com.hoo.aoo.admin.domain.house.room;

import lombok.Getter;

@Getter
public class RoomId {
    private final Long id;

    public RoomId(Long id) {
        this.id = id;
    }
}
