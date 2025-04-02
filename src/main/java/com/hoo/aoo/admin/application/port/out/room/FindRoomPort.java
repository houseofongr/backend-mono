package com.hoo.aoo.admin.application.port.out.room;

import com.hoo.aoo.admin.domain.house.room.Room;

import java.util.List;
import java.util.Optional;

public interface FindRoomPort {
    boolean exist(Long id);
    Optional<Room> load(Long id);
    List<Room> loadAll(List<Long> ids);
}
