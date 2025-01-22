package com.hoo.aoo.admin.application.port.out.room;

import com.hoo.aoo.admin.application.port.in.room.QueryRoomResult;

import java.util.Optional;

public interface FindRoomPort {
    Optional<QueryRoomResult> findResult(Long id);
}
