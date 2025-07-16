package com.hoo.main.application.port.out.persistence.home;

import com.hoo.main.application.port.in.home.*;

public interface QueryHomePort {
    QueryUserHomesResult queryUserHomes(Long userId);

    QueryHomeRoomsResult queryHomeRooms(Long homeId);

    QueryRoomItemsResult queryRoomItems(Long homeId, Long roomId);

    QueryItemSoundSourcesResult queryItemSoundSources(Long itemId);

    QuerySoundSourceResult querySoundSource(Long soundSourceId);

    QuerySoundSourcesPathResult querySoundSourcesPath(QuerySoundSourcesPathCommand command);
}
