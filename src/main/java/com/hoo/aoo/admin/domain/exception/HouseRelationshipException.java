package com.hoo.aoo.admin.domain.exception;

import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.house.room.RoomId;

public class HouseRelationshipException extends Throwable {
    public HouseRelationshipException(RoomId roomId, HouseId houseId) {
        super("Room name " + roomId.getName() + " doesn't related to " + houseId);
    }
}
