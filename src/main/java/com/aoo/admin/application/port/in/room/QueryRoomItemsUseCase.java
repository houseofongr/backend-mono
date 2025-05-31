package com.aoo.admin.application.port.in.room;

public interface QueryRoomItemsUseCase {
    QueryRoomItemsResult queryRoomItems(Long homeId, Long roomId);
}
