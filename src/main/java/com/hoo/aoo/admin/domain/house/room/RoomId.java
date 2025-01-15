package com.hoo.aoo.admin.domain.house.room;

import com.hoo.aoo.admin.domain.house.HouseId;
import lombok.Getter;

@Getter
public class RoomId {

    private final HouseId houseId;
    private String name;

    public RoomId(HouseId houseId, String name) {
        this.houseId = houseId;
        this.name = name;
    }

    public void update(String name) {
        this.name = name == null? this.name : name;
    }
}
