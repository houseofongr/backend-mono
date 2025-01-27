package com.hoo.aoo.admin.application.service.room;


import com.hoo.aoo.admin.application.port.in.room.UpdateRoomInfoCommand;
import com.hoo.aoo.admin.application.port.in.room.UpdateRoomInfoUseCase;
import com.hoo.aoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.aoo.admin.application.port.out.room.UpdateRoomPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.room.Room;
import com.hoo.aoo.common.adapter.in.web.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateRoomService implements UpdateRoomInfoUseCase {

    private final FindRoomPort findRoomPort;
    private final UpdateRoomPort updateRoomPort;

    @Override
    @Transactional
    public MessageDto update(UpdateRoomInfoCommand command) {
        try {
            List<Long> ids = command.roomInfos().stream().map(UpdateRoomInfoCommand.RoomInfo::roomId).toList();

            List<Room> rooms = findRoomPort.loadAll(ids);

            loop:
            for (UpdateRoomInfoCommand.RoomInfo roomInfo : command.roomInfos()) {
                for (Room room : rooms) {
                    if (!room.getRoomId().getId().equals(roomInfo.roomId())) continue;
                    room.updateDetail(roomInfo.newName());
                    break loop;
                }
            }

            int updateCount = updateRoomPort.update(rooms);

            return new MessageDto(updateCount + "개 룸의 정보 수정이 완료되었습니다.");

        } catch (AreaLimitExceededException | AxisLimitExceededException e) {
            throw new AdminException(AdminErrorCode.LOAD_ENTITY_FAILED);
        }
    }
}
