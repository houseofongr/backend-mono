package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.application.port.in.ReadHouseListCommand;
import com.hoo.aoo.admin.application.port.out.LoadHousePort;
import com.hoo.aoo.admin.application.port.out.database.QueryHousePort;
import com.hoo.aoo.admin.application.port.out.database.SaveHousePort;
import com.hoo.aoo.admin.application.port.out.database.UpdateHousePort;
import com.hoo.aoo.admin.domain.house.House;
import com.hoo.aoo.admin.domain.house.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HousePersistenceAdapter implements SaveHousePort, QueryHousePort, UpdateHousePort, LoadHousePort {

    private final HouseJpaRepository houseJpaRepository;
    private final HouseMapper houseMapper;

    @Override
    public Long save(House house, List<Room> rooms, Long houseImageId, Long borderImageId) {

        List<RoomJpaEntity> roomJpaEntities = rooms.stream().map(houseMapper::mapToNewEntity).toList();
        HouseJpaEntity houseJpaEntity = houseMapper.mapToNewEntity(house, roomJpaEntities, houseImageId, borderImageId);

        houseJpaRepository.save(houseJpaEntity);

        return houseJpaEntity.getId();
    }

    @Override
    public Page<HouseJpaEntity> pageQuery(ReadHouseListCommand command) {
        return houseJpaRepository.searchByCommand(command);
    }

    @Override
    public Optional<HouseJpaEntity> query(Long id) {
        return houseJpaRepository.findById(id);
    }

    @Override
    public Optional<House> load(Long houseId) {
        return null;
    }

    @Override
    public void update(House house, Map<String, Long> imageIdMap) {

    }
}
