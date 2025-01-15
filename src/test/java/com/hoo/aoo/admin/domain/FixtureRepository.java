package com.hoo.aoo.admin.domain;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.house.room.Room;

public class FixtureRepository {

    public static Room getRoom(HouseId houseId, String name) throws AxisLimitExceededException, AreaLimitExceededException {
        return Room.create(houseId, name, 0f, 0f, 0f, 1f, 1f, 1L);
    }
}
