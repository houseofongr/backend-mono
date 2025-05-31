package com.aoo.aar.application.port.in.home;

public interface QueryHomeRoomsUseCase {
    QueryHomeRoomsResult queryHomeRooms(Long userId, Long homeId);
}
