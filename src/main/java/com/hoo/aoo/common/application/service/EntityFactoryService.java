package com.hoo.aoo.common.application.service;

import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.house.Detail;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.HouseId;
import com.hoo.aoo.admin.domain.house.room.Room;
import com.hoo.aoo.common.application.port.in.CreateHousePort;
import com.hoo.aoo.common.application.port.out.IssueIdPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntityFactoryService implements CreateHousePort {

    private final IssueIdPort issueHouseIdPort;

    @Override
    public House createHouse(Detail detail, Float width, Float height, Long defaultImageFileId, Long borderImageFileId, List<Room> rooms) throws AreaLimitExceededException {
        HouseId newId = new HouseId(issueHouseIdPort.issueHouseId());
        return House.create(newId, detail, width, height, defaultImageFileId, borderImageFileId, rooms);
    }
}
