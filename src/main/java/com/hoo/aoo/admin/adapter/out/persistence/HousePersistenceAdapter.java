package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.application.port.out.database.SaveHousePort;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HousePersistenceAdapter implements SaveHousePort {

    private final HouseJpaRepository houseJpaRepository;
    private final HouseMapper houseMapper;

    @Override
    public Long save(House house, List<Room> rooms) {

        List<RoomJpaEntity> roomJpaEntities = rooms.stream().map(houseMapper::mapToNewEntity).toList();
        HouseJpaEntity houseJpaEntity = houseMapper.mapToNewEntity(house, roomJpaEntities);

        houseJpaRepository.save(houseJpaEntity);

        return houseJpaEntity.getId();
    }

}
