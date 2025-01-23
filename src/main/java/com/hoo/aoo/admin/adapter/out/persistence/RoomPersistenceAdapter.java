package com.hoo.aoo.admin.adapter.out.persistence;

import com.hoo.aoo.admin.adapter.out.persistence.entity.RoomJpaEntity;
import com.hoo.aoo.admin.adapter.out.persistence.mapper.HouseMapper;
import com.hoo.aoo.admin.adapter.out.persistence.repository.RoomJpaRepository;
import com.hoo.aoo.admin.application.port.in.room.QueryRoomResult;
import com.hoo.aoo.admin.application.port.in.room.UpdateRoomInfoCommand;
import com.hoo.aoo.admin.application.port.out.room.DeleteRoomPort;
import com.hoo.aoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.aoo.admin.application.port.out.room.UpdateRoomPort;
import com.hoo.aoo.admin.domain.house.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoomPersistenceAdapter implements UpdateRoomPort, FindRoomPort, DeleteRoomPort {

    private final RoomJpaRepository roomJpaRepository;
    private final HouseMapper houseMapper;

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
    public Optional<Room> load(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<QueryRoomResult> findResult(Long id) {
        return roomJpaRepository.findById(id).map(houseMapper::mapToQueryRoomResult);
    }


    @Override
    public void deleteRoom(Long id) {
        roomJpaRepository.deleteById(id);
    }
}
