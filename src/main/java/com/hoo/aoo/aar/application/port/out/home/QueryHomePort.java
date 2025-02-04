package com.hoo.aoo.aar.application.port.out.home;

import com.hoo.aoo.aar.application.port.in.home.QueryHomeRoomsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryRoomItemsResult;
import com.hoo.aoo.aar.application.port.in.home.QueryUserHomesResult;

public interface QueryHomePort {
    QueryUserHomesResult queryUserHomes(Long userId);
    QueryHomeRoomsResult queryHomeRooms(Long homeId);
    QueryRoomItemsResult queryRoomItems(Long roomId);
}
