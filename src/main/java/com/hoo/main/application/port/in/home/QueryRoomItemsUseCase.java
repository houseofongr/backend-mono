package com.hoo.main.application.port.in.home;

public interface QueryRoomItemsUseCase {
    QueryRoomItemsResult queryRoomItems(Long userId, Long homeId, Long roomId);
}
