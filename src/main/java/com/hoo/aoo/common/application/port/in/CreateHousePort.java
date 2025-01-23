package com.hoo.aoo.common.application.port.in;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.house.Detail;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.room.Room;

import java.util.List;

public interface CreateHousePort {
    House createHouse(Detail detail, Float width, Float height, Long defaultImageFileId, Long borderImageFileId, List<Room> rooms) throws AreaLimitExceededException;
}
