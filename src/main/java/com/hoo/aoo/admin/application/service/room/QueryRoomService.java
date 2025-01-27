package com.hoo.aoo.admin.application.service.room;

import com.hoo.aoo.admin.application.port.in.room.QueryRoomInfoUseCase;
import com.hoo.aoo.admin.application.port.in.room.QueryRoomResult;
import com.hoo.aoo.admin.application.port.out.room.FindRoomPort;
import com.hoo.aoo.admin.application.service.AdminErrorCode;
import com.hoo.aoo.admin.application.service.AdminException;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryRoomService implements QueryRoomInfoUseCase {

    private final FindRoomPort findRoomPort;

    @Override
    @Transactional(readOnly = true)
    public QueryRoomResult queryRoom(Long id) {
        try {
            Room room = findRoomPort.load(id)
                    .orElseThrow(() -> new AdminException(AdminErrorCode.ROOM_NOT_FOUND));

            return QueryRoomResult.of(room);

        } catch (AreaLimitExceededException | AxisLimitExceededException e) {
            throw new AdminException(AdminErrorCode.LOAD_ENTITY_FAILED);
        }

    }
}
