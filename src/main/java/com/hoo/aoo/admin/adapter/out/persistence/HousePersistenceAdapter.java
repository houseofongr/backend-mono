package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.HouseJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.HouseJpaRepository;
import com.hoo.aoo.admin.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.aoo.admin.application.port.in.house.QueryHouseListCommand;
import com.hoo.aoo.admin.application.port.in.house.UpdateRoomInfoCommand;
import com.hoo.aoo.admin.application.port.out.house.*;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.exception.HouseRelationshipException;
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
                room -> houseMapper.mapToNewJpaEntity(
                        room, imageFileIdMap.get(room.getId().getName())
                )
        ).toList();

        HouseJpaEntity houseJpaEntity = houseMapper.mapToNewJpaEntity(house, roomJpaEntities, imageFileIdMap);

        houseJpaRepository.save(houseJpaEntity);

        return houseJpaEntity.getId();
    }

    public Optional<HouseJpaEntity> findHouseJpaEntity(Long id) {
        return houseJpaRepository.findById(id);
    }

    @Override
    public Optional<House> find(Long id) throws AreaLimitExceededException, AxisLimitExceededException, HouseRelationshipException {

        Optional<HouseJpaEntity> optional = findHouseJpaEntity(id);

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
    public int update(UpdateRoomInfoCommand command) {

        List<Long> roomIds = command.roomInfos().stream().map(UpdateRoomInfoCommand.RoomInfo::roomId).toList();

        List<RoomJpaEntity> roomJpaEntities = roomJpaRepository.findAllById(roomIds);

        int updateCount = 0;

        for (RoomJpaEntity roomJpaEntity : roomJpaEntities) {
            for (UpdateRoomInfoCommand.RoomInfo roomInfo : command.roomInfos()) {
                if (roomJpaEntity.getId().equals(roomInfo.roomId())){
                    roomJpaEntity.updateInfo(roomInfo.newName());
                    updateCount++;
                }
            }
        }

        return updateCount;
    }

    @Override
    public Optional<RoomJpaEntity> findRoomJpaEntity(Long id) {
        return roomJpaRepository.findById(id);
    }

    @Override
    public void deleteHouse(Long id) {
        roomJpaRepository.deleteAllByHouseId(id);
        houseJpaRepository.deleteById(id);
    }

    @Override
    public void deleteRoom(Long id) {
        roomJpaRepository.deleteById(id);
    }

}
