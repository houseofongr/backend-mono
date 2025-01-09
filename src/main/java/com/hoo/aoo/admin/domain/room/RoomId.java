package com.hoo.aoo.admin.domain.room;

import com.hoo.aoo.admin.domain.house.HouseId;
import lombok.Getter;

@Getter
public class RoomId {

    private final HouseId houseId;
    private final String name;

    public RoomId(HouseId houseId, String name) {
        this.houseId = houseId;
        this.name = name;
    }
}
