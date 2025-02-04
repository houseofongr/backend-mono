package com.hoo.aoo.aar.application.port.in.home;

public interface QueryHomeRoomsUseCase {
    QueryHomeRoomsResult queryHomeRooms(Long userId, Long homeId);
}
