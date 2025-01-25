package com.hoo.aoo.admin.application.port.out.room;

import com.hoo.aoo.admin.application.port.in.room.QueryRoomResult;
import com.hoo.aoo.admin.domain.exception.AreaLimitExceededException;
import com.hoo.aoo.admin.domain.exception.AxisLimitExceededException;
import com.hoo.aoo.admin.domain.room.Room;

import java.util.Optional;

public interface FindRoomPort {
    boolean exist(Long id);

    Optional<Room> load(Long id) throws AreaLimitExceededException, AxisLimitExceededException;

    Optional<QueryRoomResult> findResult(Long id);
}
