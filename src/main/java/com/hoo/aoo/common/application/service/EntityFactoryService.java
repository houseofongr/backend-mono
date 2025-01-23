package com.hoo.aoo.common.application.service;

import com.hoo.aoo.admin.application.port.out.house.CreateRoomPort;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.house.room.Detail;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.admin.application.port.out.house.CreateHousePort;
import com.hoo.aoo.admin.domain.house.room.RoomId;
import com.hoo.aoo.common.application.port.out.IssueIdPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityFactoryService implements CreateHousePort, CreateRoomPort {

    private final IssueIdPort issueIdPort;

    @Override
    public House createHouse(com.hoo.aoo.admin.domain.house.Detail detail, Float width, Float height, Long defaultImageFileId, Long borderImageFileId, List<Room> rooms) throws AreaLimitExceededException {
        Long newId = issueIdPort.issueHouseId();
        return House.create(newId, detail, width, height, defaultImageFileId, borderImageFileId, rooms);
    }

    @Override
    public Room createRoom(String name, Float x, Float y, Float z, Float width, Float height, Long imageFileId) throws AxisLimitExceededException, AreaLimitExceededException {
        Long newId = issueIdPort.issueRoomId();
        return Room.create(newId,name,x,y,z,width,height, imageFileId);
    }
}
