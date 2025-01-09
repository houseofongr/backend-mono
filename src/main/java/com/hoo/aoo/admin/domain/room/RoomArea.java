package com.hoo.aoo.admin.domain.room;

import lombok.Getter;

@Getter
public class RoomArea {

    private final Integer width;
    private final Integer height;

    public RoomArea(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }
}
