package com.hoo.main.application.port.in.home;

public interface QueryHomeRoomsUseCase {
    QueryHomeRoomsResult queryHomeRooms(Long userId, Long homeId);
}
