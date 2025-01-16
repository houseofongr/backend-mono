package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.aoo.admin.application.port.in.QueryHouseListCommand;
import com.hoo.aoo.admin.application.port.out.*;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
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
public class HousePersistenceAdapter implements SaveHousePort, SearchHousePort, UpdateHousePort, UpdateRoomPort, FindHousePort, FindRoomPort, DeleteHousePort, DeleteRoomPort {

    private final HouseJpaRepository houseJpaRepository;
    private final RoomJpaRepository roomJpaRepository;
    private final HouseMapper houseMapper;

    @Override
    public Long save(House house, List<Room> rooms, Map<String, Long> imageFileIdMap) {

        List<RoomJpaEntity> roomJpaEntities = rooms.stream().map(
                room -> houseMapper.mapToNewEntity(
                        room, imageFileIdMap.get(room.getId().getName())
                )
        ).toList();

        HouseJpaEntity houseJpaEntity = houseMapper.mapToNewEntity(house, roomJpaEntities, imageFileIdMap);

        houseJpaRepository.save(houseJpaEntity);

        return houseJpaEntity.getId();
    }

    @Override
    public Optional<HouseJpaEntity> findJpaEntity(Long id) {
        return houseJpaRepository.findById(id);
    }

    @Override
    public Optional<House> find(Long id) throws AreaLimitExceededException, AxisLimitExceededException {

        Optional<HouseJpaEntity> optional = findJpaEntity(id);

        if (optional.isEmpty()) return Optional.empty();

        List<RoomJpaEntity> roomJpaEntities = roomJpaRepository.findAllByHouseId(id);

        return Optional.ofNullable(houseMapper.mapToDomainEntity(optional.get(), roomJpaEntities));
    }

    @Override
    public Page<HouseJpaEntity> search(QueryHouseListCommand command) {
        return houseJpaRepository.searchByCommand(command);
    }

    @Override
    public void update(Long id, House house) {

        HouseJpaEntity entity = houseJpaRepository.findById(id).orElseThrow();

        entity.updateInfo(house.getId().getTitle(), house.getId().getAuthor(), house.getId().getDescription());

    }

    @Override
    public void update(Long houseId, String originalName, Room room) {

        List<RoomJpaEntity> roomJpaEntities = roomJpaRepository.findAllByHouseId(houseId);

        for (RoomJpaEntity roomJpaEntity : roomJpaEntities) {
            if (roomJpaEntity.getName().equals(originalName)){
                roomJpaEntity.updateInfo(room.getId().getName());
                return;
            }
        }
    }

    @Override
    public Optional<RoomJpaEntity> findJpaEntity(Long id, String roomName) {
        return roomJpaRepository.findByHouseIdAndName(id, roomName);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void delete(Long houseId, String roomName) {

    }
}
