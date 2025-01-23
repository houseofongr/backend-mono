package com.hoo.aoo.admin.application.port.out.room;

import com.hoo.aoo.admin.application.port.in.room.QueryRoomResult;
import com.hoo.aoo.admin.domain.house.room.Room;

import java.util.Optional;

public interface FindRoomPort {
    Optional<Room> load(Long id);
    Optional<QueryRoomResult> findResult(Long id);
}
