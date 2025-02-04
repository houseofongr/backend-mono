package com.hoo.aoo.aar.application.port.out.home;

import com.hoo.aoo.aar.application.port.in.home.*;

public interface QueryHomePort {
    QueryUserHomesResult queryUserHomes(Long userId);
    QueryHomeRoomsResult queryHomeRooms(Long homeId);
    QueryRoomItemsResult queryRoomItems(Long roomId);
    QueryItemSoundSourcesResult queryItemSoundSources(Long itemId);
    QuerySoundSourceResult querySoundSource(Long soundSourceId);
}
